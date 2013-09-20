try:
    import sys
    import re
    try:
        input = str(sys.argv[1])
    except:
        input = ""().strip()
        
    #CLEAN UP INITIAL SPACES
    input = re.sub("\n +\n", "\n\n", input)
    
    #CLEAN UP INITIAL SPACES AND THE EXTRA LINE BREAKS
    input = re.sub("\n\s{2,}", "\n", input)

    #SPLIT UP INTO ROWS
    input = input.split("\n")

    #ITERATE ROWS
    for line in input:
         line = line.strip()
         #SPLIT ON WHITESPACE -- REMOVE LEADING AND TRAILING WS
         parts = re.split(r"\s",line,1) 

         #GET RID OF EXTRA SPACES
         ordinal= parts[0].strip()
         ordinal= ordinal.rstrip('.')
         ordinal= ordinal.rstrip(')')

         #GET RID OF EXTRA SPACES 
         content = parts[1].strip()

         #COMPOSE CHOICE
         print "  <choice label=\"ch%s\" value=\"%s\">%s</choice>" % (ordinal,ordinal, content)


except Exception, e:
    print "makeChoicesNielsenValues clip failed:"
    print e


