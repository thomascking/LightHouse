try:

    import sys
    import re
    try:
        input = str(sys.argv[1])
    except:
        input = "".strip()

    input = re.sub(r"^(\w?\d+)\.(\d+)",r"\1_\2",input)

    while "\n\n" in input:
        input = input.replace("\n\n", "\n")

    label = re.split(r"^([a-zA-Z0-9-_]+)+(\.|:|\)|\s)", input, 1)[1]
    input = re.split(r"^([a-zA-Z0-9-_]+)+(\.|:|\)|\s)", input, 1)[-1]

    rowCount = len(input.split("<row"))-1
    colCount = len(input.split("<col"))-1

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
        input_index = min(input_array)
        title = input[0:input_index]

    input = input.replace(title, "")

    output = input
    shffl = ""
    
    #DETERMINE IF WE NEED A 1D OR 2D COMMENT, SHUFFLE 2D ROWS OR COLS, ADD AVERAGES attribute.
    if ("<row" in output) and ("<col" in output):
        comment = "<comment>Select one in each row</comment>\n"
        s = output.split("    ")
        for x in s:
            if x.count("value=") > 0:
                if x.count("<col") > 0:
                    shffl = " shuffle=\"rows\""
                elif x.count("<row") > 0:
                    shffl = " shuffle=\"cols\""
    else:
        comment = "<comment>Select one</comment>\n"

    if (("<row" in output) and ("<col" in output) and (colCount > 1)) or not ("<row" in output):
        style = ''
    else:
        style = ' style=\"noGrid\" ss:questionClassNames=\"flexGrid\"'

    if "<comment>" not in input:
        print "<radio label=\"%s\"%s%s type=\"rating\">\n  <title>%s</title>\n  %s  %s\n</radio>\n<suspend/>" % (label.strip(), shffl, style, title.strip(), comment, output)
    else:
        print "<radio label=\"%s\"%s%s type=\"rating\">\n  <title>%s</title>\n  %s\n</radio>\n<suspend/>" % (label.strip(), shffl, style, title.strip(), output)

except Exception, e:
    print e

