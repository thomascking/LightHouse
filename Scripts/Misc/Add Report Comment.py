try:
    import sys
    try:
        input = str(sys.argv[1])
    except:
        input = "".strip()
    input = input.replace("\n", "<br/>")
    print "<comment label=\"\" where=\"report\">%s</comment>" % input

except Exception, e:
    print e

