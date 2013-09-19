try:
    import sys
    try:
        input = str(sys.argv[1])
    except:
        input = ""().split("\n")
    while "" in input:
        del input[input.index("")]
    for x in input:
        if "alt=" in x:
            x = x.replace(" alt=\"", " groups=\"g\" alt=\"")
        else:
            x = x.replace("\">", "\" groups=\"g\">")
        print x
except Exception, e:
    print e


