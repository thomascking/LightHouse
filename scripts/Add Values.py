try:
    import sys
    try:
        input = str(sys.argv[1])
    except:
        input = ""().split("\n")
    while "" in input:
        del input[input.index("")]
    count = 1
    for x in input:
        if "alt" in x:
            x = x.replace("\" alt=\"", "\" value=\"\" alt=\"")
            count += 1
        else:
            x = x.replace("\">", "\" value=\"\">")
            count += 1
        print x
    
except Exception, e:
    print e

