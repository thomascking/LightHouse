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
    count = 0
    for line in input:
         line = line.strip()
         #SPLIT ON WHITESPACE -- REMOVE LEADING AND TRAILING WS
         parts = re.split(r"\s",line,1) 

         #GET RID OF EXTRA SPACES
         ordinal= parts[0].strip()
         ordinal= ordinal.rstrip('.')
         ordinal= ordinal.rstrip(')')

         #GET RID OF EXTRA SPACES
         if len(parts) == 2: 
           content = parts[1].strip()


         extra=""

         if "other" in content.lower() and "specify" in content.lower():
           extra=' open=\"1\" openSize=\"45\" randomize=\"0\"'

         #COMPOSE ROW
         if ordinal[0].isalpha() and (len(parts) == 2):
           print "  <row label=\"%s\"%s>%s</row>" % (ordinal, extra, content)
         elif ordinal[0].isdigit():
           print "  <row label=\"r%s\"%s>%s</row>" % (ordinal, extra, content)
         elif (len(parts) == 2):
           print "  <row label=\"%s\"%s>%s</row>" % (ordinal, extra, content)
         else:
           count += 1
           print "  <row label=\"r%s\"%s>%s</row>" % (str(count), extra, line)

except Exception, e:
    print "makeRows clip failed:"
    print e


