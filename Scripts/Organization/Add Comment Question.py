try:
    import sys
    try:
        input = str(sys.argv[1])
    except:
        input = "".strip()
    print "  <comment>%s</comment>" % input
    
except Exception, e:
    print e

