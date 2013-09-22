try:
    import sys
    import re
    count = 0
    
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
        input[x] = re.sub("^[a-zA-Z0-9]{1,2}[\.:\)][ \t]+", "", input[x])
    
    length = len(input)
    
    for x in input:

        if "other" in input[count].strip().lower() and "specify" in input[count].strip().lower():
          extra=' open=\"1\" openSize=\"45\" randomize=\"0\"'
        else:
          extra = ''

        print "  <row label=\"r%s\" value=\"%s\"%s>%s</row>" % (str(length - count), length - count, extra, x.strip())
        count += 1
    
except Exception, e:
    print e

