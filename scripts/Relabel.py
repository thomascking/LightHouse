try:
    import sys
    import re
    try:
        input = str(sys.argv[1])
    except:
        input = ""().strip("\n").split("\n")

    startlabel = re.findall("label=['\"]\w+['\"]", input[0])[0].replace("label=","").strip("\"'") # get first label
    startelement = re.findall("<col|<row|<choice", input[0])[0]

    nonalphalabel = re.sub("[a-zA-Z]*", "", startlabel) # get non-alpha components of first label

    alphanum = False # first label does not contain numbers unless found otherwise
    if nonalphalabel.isdigit():
        startindex = int(nonalphalabel) # we'll increment from numeric portion of the first label (e.g. the "0" in "r0")
        alphanum = True
    else:
        startindex = ord(startlabel) # we'll increment from the alphabetical label (e.g. "A" if the first label is "A")

# the new labels with either be incremented from the numeric portion of the first label
# or, they'll be incremented from a length == 1 alphabetical label
    cnt=-1
    for i in range(len(input)):
        if re.search(startelement, input[i].strip()):
          cnt+=1
          newlabel = re.sub("[0-9]+", str(startindex + cnt), startlabel) if alphanum else chr(startindex + cnt)
          input[i] = re.sub("label=['\"]\w+['\"]", "label=\"%s\"" % newlabel, input[i])

    print "\n".join(input)

except Exception, e:
    print e

