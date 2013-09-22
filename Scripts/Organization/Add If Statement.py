try:
    import sys
    try:
        input = str(sys.argv[1])
    except:
        input = "".strip()
    print "<if label=\"\" cond=\"\">\n%s\n<suspend/>\n</if>" % input
    
    
except Exception, e:
    print e

