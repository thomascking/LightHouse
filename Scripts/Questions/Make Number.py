try:
    import sys
    import re
    try:
        input = str(sys.argv[1])
    except:
        input = "".strip()

    input = re.sub(r"^(\w?\d+)\.(\d+)",r"\1_\2",input)

    label = re.split(r"^([a-zA-Z0-9-_]+)+(\.|:|\)|\s)", input, 1)[1]
    input = re.split(r"^([a-zA-Z0-9-_]+)+(\.|:|\)|\s)", input, 1)[-1]

    while "\n\n" in input:
        input = input.replace("\n\n", "\n")
 
    if label[0].isdigit():
        label = "Q" + label

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

    input = input.replace(title, "")
    output = input
    if output != "":
      output = "  " + output + "\n"

    if "<comment>" not in input:
        comment = "<comment>Please enter a whole number</comment>\n"
        print "<number label=\"%s\" size=\"3\" optional=\"0\">\n  <title>%s</title>\n  %s%s</number>\n<suspend/>" % (label.strip(), title.strip(), comment, output)
    else:
        print "<number label=\"%s\" size=\"3\" optional=\"0\">\n  <title>%s</title>\n%s</number>\n<suspend/>" % (label.strip(), title.strip(), output)
    
except Exception, e:
    print e

