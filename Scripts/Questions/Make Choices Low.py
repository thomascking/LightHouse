try:
    import sys
    import re
    count = 0

    try:
        input = str(sys.argv[1])
    except:
        input = ""()

    #CLEAN UP THE TABS
    input = re.sub("\t+", " ", input)

    #CLEAN UP SPACES
    input = re.sub("\n +\n", "\n\n", input)

    #CLEAN UP THE EXTRA LINE BREAKS
    input = re.sub("\n{2,}", "\n", input)

    input = input.strip().split("\n")

    for x in range(0,len(input)):
        input[x] = re.sub("^[a-zA-Z0-9]{1,2}[\.:\)][ \t]+", "", input[x])

    for x in input:
        print "  <choice label=\"ch%s\" value=\"%s\">%s</choice>" % (str(count+1), count+1, x.strip())
        count += 1
except Exception, e:
    print e


