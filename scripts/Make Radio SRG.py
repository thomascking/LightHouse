try:
    import sys
    import re
    try:
        input = str(sys.argv[1])
    except:
        input = ""().strip()
    
    input = re.sub(r"^(\w?\d+)\.(\d+)",r"\1_\2",input)

    label = re.split(r"^([a-zA-Z0-9-_]+)+(\.|:|\)|\s)", input, 1)[1]
    input = re.split(r"^([a-zA-Z0-9-_]+)+(\.|:|\)|\s)", input, 1)[-1]

    # get rid of blank lines
    while "\n\n" in input:
        input = input.replace("\n\n", "\n")
    
    # see if there is a number
    if label[0].isdigit():
        label = "Q" + label

    #capture the title
    if "@" in input:
        title = input[0:(input.index("@"))]
    else:
        input_array = []
        if "<row" in input:
            input_array.append(input.index("<row"))
        if "<col" in input:
            input_array.append(input.index("<col"))
        if "<choice" in input:
            input_array.append(input.index("<choice"))
        if "<comment" in input:
            input_array.append(input.index("<comment"))
        if "<group" in input:
            input_array.append(input.index("<group"))
        if "<net" in input:
            input_array.append(input.index("<net"))
        if "<exec" in input:
            input_array.append(input.index("<exec"))
        input_index = min(input_array)
        title = input[0:input_index]

    # remove title from input
    input = input.replace(title, "")

    output = input
    #test for and adjust comment for 2d question
    if "<comment>" not in input:
        if ("<row" in output) and ("<col" in output):
            comment = "<comment>Please select one in each row</comment>\n"
        else:
            comment = "<comment>Please select one</comment>\n"

    # compose our new radio question
    if "<comment>" not in input:
      print "<radio label=\"%s\">\n  <title>%s</title>\n  %s  %s\n</radio>\n<suspend/>" % (label.strip(), title.strip(), comment, output)
    else:
      print "<radio label=\"%s\">\n  <title>%s</title>\n  %s\n</radio>\n<suspend/>" % (label.strip(), title.strip(), output)
    
except Exception, e:
    print "makeRadio clip failed:"
    print e


