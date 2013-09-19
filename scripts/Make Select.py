try:
    import sys
    import re
    try:
        input = str(sys.argv[1])
    except:
        input = ""().strip()

    input = re.sub(r"^(\w?\d+)\.(\d+)",r"\1_\2",input)

    #isolate label and the rest
    label = re.split(r"^([a-zA-Z0-9-_]+)+(\.|:|\)|\s)", input, 1)[1]
    input = re.split(r"^([a-zA-Z0-9-_]+)+(\.|:|\)|\s)", input, 1)[-1]
    
    #remove extra blank lines
    while "\n\n" in input:
        input = input.replace("\n\n", "\n")

    #Add a q if the first character is a digit
    if label[0].isdigit():
        label = "Q" + label

    #isolate the title
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

    #remove the title
    input = input.replace(title, "")

    # add the all important line breakage
    output = "\n  " + input

    # compose the select question
    print "<select label=\"%s\" optional=\"0\">\n  <title>%s</title>  %s\n</select>\n<suspend/>" % (label.strip(), title.strip(), output)


except Exception, e:
    print e

