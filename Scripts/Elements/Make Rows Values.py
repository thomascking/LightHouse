try:
    import sys
    import re
    try:
        input = str(sys.argv[1])
    except:
        input = "".strip()
       
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

         extra=""

         if "other" in content.lower() and "specify" in content.lower():
           extra=' open=\"1\" openSize=\"25\" randomize=\"0\"'

         #COMPOSE ROW
         print "  <row label=\"r%s\" value=\"%s\"%s>%s</row>" % (ordinal,ordinal, extra, content)


except Exception, e:
    print "makeRowsNielsen clip failed:"
    print e


