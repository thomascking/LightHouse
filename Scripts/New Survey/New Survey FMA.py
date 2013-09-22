try:
    import sys
    import re
    try:
        input = str(sys.argv[1])
    except:
        input = "".strip()
    input = re.sub("\t+", " ", input)
    input = re.sub("\n +\n", "\n\n", input)
    funkyChars = [(chr(133),'...'),(chr(145),"'"),(chr(146),"'"),(chr(147),'"'),(chr(148),'"'),(chr(151),'--')]
    for pair in funkyChars:
        input = input.replace(pair[0],pair[1])
    input = re.sub("\n{3,}", "\n\n", input)
    input = input.replace("&", "&amp;")
    input = input.replace("&amp;#", "&#")
    
    HEADER = """<?xml version="1.0" encoding="UTF-8"?>
<survey name="Survey" alt="" autosave="0" extraVariables="source,list,url,record,ipAddress,userAgent,decLang" compat="114" builderCompatible="1" secure="0" state="testing" setup="time,term,quota,decLang" ss:disableBackButton="1" fwoe="text" ss:logoFile="fma/surveysonline_logo.gif" ss:logoPosition="left">

<samplesources default="0">
  <samplesource list="0" title="default">
    <exit cond="qualified"><b>Thanks again for completing the survey!<br/><br/>Your feedback and quick response to this survey are greatly appreciated.</b></exit>
    <exit cond="terminated"><b>Thank you for your input!</b></exit>
    <exit cond="overquota"><b>Thank you for your input!</b></exit>
  </samplesource>
</samplesources>"""
    
    FOOTER = """<marker name="qualified"/>

<radio label="vStatus" title="Status">
<virtual>
if 'recovered' in markers:
    data[0][0] = 3
else:
    if 'qualified' in markers:
        data[0][0] = 2
    elif 'OQ' in markers:
        data[0][0] = 1
    else:
        data[0][0] = 0
</virtual>
  <row label="r1">Term</row>
  <row label="r2">OQ</row>
  <row label="r3">Quals</row>
  <row label="r4">Partials</row>
</radio>

</survey>"""
    
    print "%s\n\n%s\n\n%s" % (HEADER, input, FOOTER)
    
except Exception, e:
    print e

