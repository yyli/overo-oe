#
# Small event handler to automatically open URLs and file
# bug reports at a bugzilla of your choiche
#
# This class requires python2.4 because of the urllib2 usage
#


def seppuku_login(opener, login, user, password):
    """
    We need to post to query.cgi with the parameters
    Bugzilla_login and Bugzilla_password and will scan
    the resulting page then

    @param opened = cookie enabled urllib2 opener
    @param login = http://bugzilla.openmoko.org/cgi-bin/bugzilla/query.cgi?
    @param user  = Your username
    @param password  = Your password
    """
    import urllib
    param = urllib.urlencode( {"GoAheadAndLogIn" : 1, "Bugzilla_login" : user, "Bugzilla_password" : password } )
    result = opener.open(login + param)

    if result.code != 200:
        return False
    txt = result.read()
    if not '<a href="relogin.cgi">Log&nbsp;out</a>' in txt:
        return False

    return True

def seppuku_find_bug_report_old():
    from HTMLParser import HTMLParser

    class BugQueryExtractor(HTMLParser):
        STATE_NONE             = 0
        STATE_FOUND_TR         = 1
        STATE_FOUND_NUMBER     = 2
        STATE_FOUND_PRIO       = 3
        STATE_FOUND_PRIO2      = 4
        STATE_FOUND_NAME       = 5
        STATE_FOUND_PLATFORM   = 6
        STATE_FOUND_STATUS     = 7
        STATE_FOUND_WHATEVER   = 8 # I don't know this field
        STATE_FOUND_DESCRIPTION =9

        def __init__(self):
            HTMLParser.__init__(self)
            self.state = self.STATE_NONE
            self.bugs = []

        def handle_starttag(self, tag, attr):
            if self.state == self.STATE_NONE and tag.lower() == "tr":
                if len(attr) == 1 and attr[0] == ('class', 'bz_normal bz_P2 '):
                    self.state = self.STATE_FOUND_TR
            elif self.state == self.STATE_FOUND_TR and tag.lower() == "td":
                self.state += 1

        def handle_endtag(self, tag):
            if tag.lower() == "tr":
                if self.state != self.STATE_NONE:
                    self.bugs.append( (self.bug,self.status) )
                self.state = self.STATE_NONE
            if self.state > 1 and tag.lower() == "td":
                self.state += 1

        def handle_data(self,data):
            data = data.strip()

            # skip garbage
            if len(data) == 0:
                return

            if self.state == self.STATE_FOUND_NUMBER:
                self.bug = data
            elif self.state == self.STATE_FOUND_STATUS:
                self.status = data

        def result(self):
            return self.bugs

    return BugQueryExtractor()



def seppuku_find_bug_report(opener, query, product, component, bugname):
    """
    Find a bug report with the sane name and return the bug id
    and the status.

    @param opener = urllib2 opener
    @param query  = e.g. https://bugzilla.openmoko.org/cgi-bin/bugzilla/query.cgi?
    @param product = search for this product
    @param component = search for this component
    @param bugname = the bug to search for

    https://bugzilla.openmoko.org/cgi-bin/bugzilla/buglist.cgi?short_desc_type=substring&short_desc=manual+test+bug&product=OpenMoko&emailreporter2=1&emailtype2=substring&email2=freyther%40yahoo.com
    but it does not support ctype=csv...
    """
    result = opener.open("%(query)s?product=%(product)s&component=%(component)s&short_desc_type=substring&short_desc=%(bugname)s" % vars())
    if result.code != 200:
        raise "Can not query the bugzilla at all"
    txt = result.read()
    scanner = seppuku_find_bug_report_old()
    scanner.feed(txt)
    if len(scanner.result()) == 0:
        return (False,None)
    else: # silently pick the first result
        (number,status) = scanner.result()[0]
        return (not status in ["CLOS", "RESO", "VERI"],number)

def seppuku_reopen_bug(opener, file, product, component, bug_number, bugname, text):
    """
    Reopen a bug report and append to the comment

    Same as with opening a new report, some bits need to be inside the url

    http://bugzilla.openmoko.org/cgi-bin/bugzilla/process_bug.cgi?id=239&bug_file_loc=http%3A%2F%2F&version=2007&longdesclength=2&product=OpenMoko&component=autobuilds&comment=bla&priority=P2&bug_severity=normal&op_sys=Linux&rep_platform=Neo1973&knob=reopen&target_milestone=Phase+0&short_desc=foo
    """

    import urllib, urllib2
    param = urllib.urlencode( { "product" : product, "component" : component, "longdesclength" : 2,
                                "short_desc" : bugname, "knob" : "reopen", "id" : bug_number, "comment" : text } )
    try:
        result = opener.open( file + param )
    except urllib2.HTTPError, e:
        print e.geturl()
        print e.info()
        return False

    if result.code != 200:
        return False
    else:
        return True

def seppuku_file_bug(opener, file, product, component, bugname, text):
    """
    Create a completely new bug report


    http://bugzilla.openmoko.org/cgi-bin/bugzilla/post_bug.cgi?bug_file_loc=http%3A%2F%2F&version=2007&product=OpenMoko&component=autobuilds&short_desc=foo&comment=bla&priority=P2&bug_severity=normal&op_sys=Linux&rep_platform=Neo1973

    You are forced to add some default values to the bugzilla query and stop with '&'

    @param opener  urllib2 opener
    @param file    The url used to file a bug report
    @param product Product
    @param component Component
    @param bugname  Name of the to be created bug
    @param text Text
    """

    import urllib,urllib2
    param = urllib.urlencode( { "product" : product, "component" : component, "short_desc" : bugname, "comment" : text } )
    try:
        result = opener.open( file + param )
    except urllib2.HTTPError, e:
        print e.geturl()
        print e.info()
        raise e
        return False

    if result.code != 200:
        return False
    else:
        return True



addhandler seppuku_eventhandler
python seppuku_eventhandler() {
    """
    Report task failures to the bugzilla
    and succeeded builds to the box
    """
    from bb.event import NotHandled, getName
    from bb import data, mkdirhier, build
    import bb, os, glob

    bb.note( "Ran" )

    try:
        import urllib2, cookielib
    except:
        bb.note("Failed to import the cookielib and urllib2, make sure to use python2.4")
        return NotHandled

    event = e
    data = e.data
    name = getName(event)
    if name == "PkgFailed":
        if not bb.data.getVar('SEPPUKU_AUTOBUILD', data, True) == "0":
            build.exec_task('do_clean', data)
    elif name == "TaskFailed" or name == "NoProvider":
        cj = cookielib.CookieJar()
        opener  = urllib2.build_opener(urllib2.HTTPCookieProcessor(cj))
        login   = bb.data.getVar("SEPPUKU_LOGIN", data, True)
        query   = bb.data.getVar("SEPPUKU_QUERY", data, True)
        newbug  = bb.data.getVar("SEPPUKU_NEWREPORT",  data, True)
        reopen  = bb.data.getVar("SEPPUKU_ADDCOMMENT",  data, True)
        user    = bb.data.getVar("SEPPUKU_USER",  data, True)
        passw   = bb.data.getVar("SEPPUKU_PASS",  data, True)
        product = bb.data.getVar("SEPPUKU_PRODUCT", data, True)
        component = bb.data.getVar("SEPPUKU_COMPONENT", data, True)

        if not seppuku_login(opener, login, user, passw):
            bb.note("Login to bugzilla failed")
            return NotHandled
        else:
            print "Logged into the box"

        if name == "TaskFailed":
            bugname = "%(package)s-%(pv)s-%(pr)s-%(task)s" % { "package" : bb.data.getVar("PN", data, True),
                                                               "pv"      : bb.data.getVar("PV", data, True),
                                                               "pr"      : bb.data.getVar("PR", data, True),
                                                               "task"    : e.task }
            log_file = glob.glob("%s/log.%s.*" % (bb.data.getVar('T', event.data, True), event.task))
            if len(log_file) != 0:
                to_file  = bb.data.getVar('TINDER_LOG', event.data, True)
            text    = "".join(open(log_file[0], 'r').readlines())
        elif name == "NoProvider":
            bugname = "noprovider for %s runtime: %s" % (event.getItem, event.getisRuntime)
            text    = "Please fix it"
        else:
            assert False

        (bug_open, bug_number) = seppuku_find_bug_report(opener, query, product, component, bugname)

        bb.note("Bug is open: %s and bug number: %s" % (bug_open, bug_number))

        # The bug is present and still open, no need to attach an error log
        if bug_number and bug_open:
            bb.note("The bug is known as '%s'" % bug_number)
            return NotHandled

        if bug_number and not bug_open:
            if not seppuku_reopen_bug(opener, reopen, product, component, bug_number, bugname, text):
                bb.note("Failed to reopen the bug report")
        elif not seppuku_file_bug(opener, newbug, product, component, bugname, text):
            bb.note("Filing a bugreport failed")

    return NotHandled
}