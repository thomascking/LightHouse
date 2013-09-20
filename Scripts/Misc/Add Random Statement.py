try:
    import sys
    try:
        input = str(sys.argv[1])
    except:
        input = ""().strip()
    print "<random label=\"\">\n%s\n</random>" % input
    
except Exception, e:
    print e

