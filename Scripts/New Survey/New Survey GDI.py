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
<survey name="Survey" alt="" autosave="0" extraVariables="source,list,url,record,ipAddress,userAgent,decLang" compat="114" builderCompatible="1" secure="0" state="testing" setup="time,term,quota,decLang" ss:disableBackButton="1" fixedWidth="tight" zeroPad="1">

<samplesources default="1">
  <samplesource list="1" title="Greenfield/Toluna">
    <var name="gid" unique="1"/>
    <exit cond="qualified" url="http://ups.surveyrouter.com/soqualified.aspx?gid=${gid}"/>
    <exit cond="terminated" url="http://ups.surveyrouter.com/soterminated.aspx?gid=${gid}"/>
    <exit cond="overquota" url="http://ups.surveyrouter.com/soquotafull.aspx?gid=${gid}"/>
  </samplesource>
</samplesources>

<number altlabel="record" fwidth="10" label="vrec" size="10" title="Record As Number" virtual="if record:  data[0][0] = int(record)"/>
"""
    
    FOOTER = """<marker name="qualified"/>

</survey>"""
    
    print "%s\n%s\n\n%s" % (HEADER, input, FOOTER)
    
except Exception, e:
    print e

