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
<survey name="Survey" alt="" autosave="1" autosaveKey="ac" extraVariables="source,list,url,record,ipAddress,userAgent,flashDetected,ac,sn,lang,co,decLang" setup="time,term,quota,decLang" ss:disableBackButton="1" displayOnError="all" unique="" compat="114" builderCompatible="1" secure="0" state="testing">

<exec when="init">
db_completed = Database( name="completed" )
</exec>
<exec>
db_id = ac
p.completedID = db_id
</exec>

<samplesources default="1">
  <completed>It seems you have already entered this survey.</completed>
  <invalid>You are missing information in the URL. Please verify the URL with the original invite.</invalid>
  <samplesource list="1" title="GMI">
    <var name="ac" unique="1"/>
    <var name="sn" required="1"/>
    <var name="lang" required="1"/>
    <exit cond="qualified" url="http://globaltestmarket.com/20/survey/finished.phtml?ac=${ac}&amp;sn=${sn}&amp;lang=${lang}"/>
    <exit cond="terminated" url="http://globaltestmarket.com/20/survey/finished.phtml?ac=${ac}&amp;sn=${sn}&amp;lang=${lang}&amp;sco=s"/>
    <exit cond="overquota" url="http://globaltestmarket.com/20/survey/finished.phtml?ac=${ac}&amp;sn=${sn}&amp;lang=${lang}&amp;sco=o"/>
  </samplesource>
<samplesources>

<html cond="db_completed.has(p.completedID)" final="1" label="dupe" where="survey">It seems you have already participated in this survey.</html>
"""
    
    FOOTER = """<marker name="qualified"/>

<exec when="finished">
if gv.survey and gv.survey.root.state.live:
    db_completed.add(p.completedID)
</exec>

</survey>"""
    
    print "%s\n\n%s\n\n%s" % (HEADER, input, FOOTER)
    
except Exception, e:
    print e

