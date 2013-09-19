try:
    import sys
    try:
        input = str(sys.argv[1])
    except:
        input = ""().strip()
    print "<a href=\"%s\" target=\"_blank\">%s</a>" % (input,input)
except Exception, e:
    print e

