try:
    import sys
    import re
    try:
        input = str(sys.argv[1])
    except:
        input = ""
    
    #CLEAN UP THE TABS
    input = re.sub("\t+", " ", input)
    
    #CLEAN UP SPACES
    input = re.sub("\n +\n", "\n\n", input)
    
    #CLEAN UP THE EXTRA LINE BREAKS
    input = re.sub("\n{2,}", "\n", input)
    
    input = input.strip().split("\n")
    
    for x in range(0,len(input)):
        input[x] = re.sub("^[a-zA-Z0-9]{1,2}[\.:\)][ \t]+", "\n", input[x])
    count = 0
    for x in input:
        print "<res label=\"\">%s</res>" % input[count].strip()
        count += 1
except Exception, e:
    print e

