try:
    import sys
    import re
    try:
        input = str(sys.argv[1])
    except:
        input = ""().strip()
    
    input = re.sub(r"^(\w?\d+)\.(\d+)",r"\1_\2",input)

    # isolate the label
    label = re.split(r"^([a-zA-Z0-9-_]+)+(\.|:|\)|\s)", input, 1)[1]
    # isolate the rest
    input = re.split(r"^([a-zA-Z0-9-_]+)+(\.|:|\)|\s)", input, 1)[-1]
    
    # remove spaces
    while "\n\n" in input:
        input = input.replace("\n\n", "\n")

    # add a q to the label if its a digit
    if label[0].isdigit():
        label = "Q" + label

    # isolate the title from either a macro or a bunch of rows or columns
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
    
    # take the title out of the input
    input = input.replace(title, "")

    # add the all important line breakage
    output2 = input

    nota_array = [">None of the above",">None of these"]
    for nota in nota_array:
      if nota in output2:
        repwith = " exclusive=\"1\" randomize=\"0\"" + nota
        output = output2.replace(nota,repwith)
        output2 = output
      else:
        output = output2

    # compose the question
    print "<checkbox label=\"%s\" atleast=\"1\">\n  <title>%s</title>\n  %s\n</checkbox>\n<suspend/>" % (label.strip(), title.strip(), output)

except Exception, e:
    print e

