try:
    import sys
    import re
    try:
        input = str(sys.argv[1])
    except:
        input = ""()
    input = re.sub("(<(row|col|choice|group).*?>)(.*?)(</(row|col|choice|group)>)", "\g<1><alt>\g<3></alt>\g<3>\g<4>", input)
    title = re.compile("(<title>)(.*?)(</title>)", re.DOTALL)
    input = title.sub("\g<1>\g<2>\g<3>\n<alt>\g<2></alt>", input)
    print input
    
except Exception, e:
    print e

