try:
    import sys
    import re
    try:
        input = str(sys.argv[1])
    except:
        input = "".strip("\n").split("\n")

    for i in range(len(input)):
        if "<row" in input[i]:
            objx = "row"
            labx = "r"
            objy = "col"
            laby = "c"
        elif "<col" in input[i]:
            objx = "col"
            labx = "c"
            objy = "row"
            laby = "r"
        input[i] = re.sub("(<|\/)"+objx,'\\1'+objy,input[i])
        input[i] = re.sub("label=(\"|')"+labx+"(\\d)",'label=\\1'+laby+'\\2',input[i])
    print "\n".join(input)
except Exception, e:
    print e