try:
    import sys
    try:
        input = str(sys.argv[1])
    except:
        input = ""().strip()
    print "<html label=\"\" cond=\"\" where=\"survey\" final=\"1\"><div class=\"error\">%s</div></html>" % input
except Exception, e:
    print e


