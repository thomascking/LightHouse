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

    while "\n\n" in input:
        input = input.replace("\n\n", "\n")
    if label[0].isdigit():
        label = "Q" + label

    #ISOLATE TITLE TO MACRO OR QUESTION ELEMENT OPEN ANGLE BRACKET
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
        if len(input_array) == 0:
            title = input
        else:
          input_index = min(input_array)
          title = input[0:input_index]

    #REMOVE THE TITLE TEXT
    input = input.replace(title, "")

    # add the all important line breakage
    output = input
    if output != "":
      output = "  " + output + "\n"

    #COMPOSE OUR QUESTION
    if "<comment>" not in input:
        comment = "<comment>Please be as specific as possible</comment>\n"
        print "<text label=\"%s\" size=\"40\" optional=\"0\">\n  <title>%s</title>\n  %s  %s</text>\n<suspend/>" % (label.strip(), title.strip(), comment, output)
    else:
        print "<text label=\"%s\" size=\"40\" optional=\"0\">\n  <title>%s</title>\n  %s</text>\n<suspend/>" % (label.strip(), title.strip(), output)
    
except Exception, e:
    print e

