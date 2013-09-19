try:
    import sys
    import re
    try:
        input = str(sys.argv[1])
    except:
        input = ""()

    input = input.strip().split("\n")

    for x in range(0,len(input)):
        input[x] = re.sub("^[a-zA-Z0-9]{1,2}[\.:\)][ \t]+", "\n", input[x])
    count = 0
    for x in input:
        print "  <choice label=\"ch%s\">%s</choice>" % (str(count+1), input[count].strip())
        count += 1
except Exception, e:
    print e


