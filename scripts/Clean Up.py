
try:
    import sys
    import re
    try:
        input = str(sys.argv[1])
    except:
        input = ""().strip()

    #CLEAN UP THE TABS
    input = re.sub("\t+", " ", input)

    #CLEAN UP SPACES 
    input = re.sub("\n +\n", "\n\n", input)

    #REPLACE SMART QUOTES, ELLIPSIS AND EM-DASHES
    funkyChars = [(chr(133),'...'),(chr(145),"'"),(chr(146),"'"),(chr(147),'"'),(chr(148),'"'),(chr(151),'--')]

    for pair in funkyChars:
        input = input.replace(pair[0],pair[1])

    #CLEAN UP THE EXTRA LINE BREAKS
    input = re.sub("\n{3,}", "\n\n", input)

    #REPLACE AMPERSTANDS WITH ENTITIES
    input = input.replace("&", "&amp;")

    print input

except Exception, e:
    print e

