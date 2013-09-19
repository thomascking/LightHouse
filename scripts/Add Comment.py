try:
    import sys
    try:
        input = str(sys.argv[1])
    except:
        input = ""().strip()
    input = input.replace("\n", "<br/>\n")
    print "<html label=\"\" where=\"survey\">%s</html>" % input

except Exception, e:
    print e

