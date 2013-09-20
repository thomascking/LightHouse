try:
    import sys
    try:
        input = str(sys.argv[1])
    except:
        input = ""()

    while "\n\n" in input:
        input = input.replace("\n\n", "\n")

    while input[0] == "\n":
        input = input[1:]

    while input[-1] == "\n":
        input = input[:-1]

    print '\n'.join('<!--%s-->' % i.strip() for i in input.splitlines())
except Exception, e:
    print e





