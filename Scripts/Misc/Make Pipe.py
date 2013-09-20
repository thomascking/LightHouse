try:
    import sys
    import re
    try:
        input = str(sys.argv[1])
    except:
        input = ""().strip()
    
    # get rid of blank lines
    while "\n\n" in input:
        input = input.replace("\n\n", "\n")

    output = input

    # compose our new pipe tag
    print "<pipe label=\"\" capture=\"\">\n  %s\n</pipe>\n" % (output)
    
except Exception, e:
    print "makePipe clip failed:"
    print e


