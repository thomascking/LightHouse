try:
    import sys
    try:
        input = str(sys.argv[1])
    except:
        input = ""().strip()
    print "<if label=\"\" cond=\"1\" randomize=\"1\">\n%s\n</if>" % input
    
except Exception, e:
    print e

