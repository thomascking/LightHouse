try:
    import sys
    import re
    try:
        input = str(sys.argv[1])
    except:
        input = ""().strip()
    input = re.sub("\t+", " ", input)
    input = re.sub("\n +\n", "\n\n", input)
    funkyChars = [(chr(133),'...'),(chr(145),"'"),(chr(146),"'"),(chr(147),'"'),(chr(148),'"'),(chr(151),'--')]
    for pair in funkyChars:
        input = input.replace(pair[0],pair[1])
    input = re.sub("\n{3,}", "\n\n", input)
    input = input.replace("&", "&amp;")
    input = input.replace("&amp;#", "&#")
    
    HEADER = """<?xml version="1.0" encoding="UTF-8"?>
<survey name="Survey" alt="" autosave="1" extraVariables="source,list,url,record,ipAddress,userAgent,decLang" compat="114" builderCompatible="1" secure="0" state="testing" setup="time,term,quota,decLang" ss:disableBackButton="1" trackCheckbox="1" unique="">

<!-- IMPORTANT: Remember to copy the nstyles file from v2/cmb/temp-cmb to the current project directory -->

<samplesources default="0">
  <samplesource list="0" title="default">
    <exit cond="qualified"><b>Thanks again for completing the survey!<br/><br/>Your feedback and quick response to this survey are greatly appreciated.</b></exit>
    <exit cond="terminated"><b>Thank you for your input!</b></exit>
    <exit cond="overquota"><b>Thank you for your input!</b></exit>
  </samplesource>
</samplesources>"""
    
    FOOTER = """<marker name="qualified"/>

</survey>"""
    
    print "%s\n\n%s\n\n%s" % (HEADER, input, FOOTER)
    
except Exception, e:
    print e

