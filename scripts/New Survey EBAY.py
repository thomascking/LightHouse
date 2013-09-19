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
<survey name="eBay Survey" alt="" autosave="1" extraVariables="source,list,url,record,ipAddress,userAgent,co,decLang" compat="114" builderCompatible="1" secure="0" state="testing" setup="time,term,quota,decLang" ss:disableBackButton="1" displayOnError="all" unique="" lang="english" otherLanguages="danish,german,finnish,french,norwegian,spanish,swedish,uk">

<res label="privacyText">Privacy Policy</res>
<res label="helpText">Help</res>

<!-- Remove or add countries as needed -->
<languages default="english">
  <language name="danish" var="co" value="dk"/>
  <language name="german" var="co" value="de"/>
  <language name="finnish" var="co" value="fi"/>
  <language name="french" var="co" value="fr"/>
  <language name="norwegian" var="co" value="no"/>
  <language name="spanish" var="co" value="es"/>
  <language name="swedish" var="co" value="se"/>
  <language name="uk" var="co" value="uk"/>
  <language name="english" var="co" value="us"/>
</languages>

<!-- Remove or add countries as needed -->
<radio label="vco" title="Country" virtual="bucketize(co)">
  <row label="dk">Denmark</row>
  <row label="de">Germany</row>
  <row label="fi">Finland</row>
  <row label="fr">France</row>
  <row label="no">Norway</row>
  <row label="es">Spain</row>
  <row label="se">Sweden</row>
  <row label="uk">United Kingdom</row>
  <row label="us">United Sates</row>
</radio>

<!-- Remove or add countries as needed -->
<samplesources default="1">
  <samplesource list="1" title="eBay Sample">
 <!--  <var name="source" filename="invited.txt" unique="1"/>  un-comment this before launching -->
   <var name="co" required="1" values="dk,de,fi,fr,no,es,se,uk,us"/>
    <exit cond="qualified and co=='dk'" timeout="8" url="http://www.ebay.dk">Survey Completed - Thank you for your time and opinions!</exit>
    <exit cond="terminated and co=='dk'" timeout="8" url="http://www.ebay.dk">Thank you for your input!</exit>
    <exit cond="overquota and co=='dk'" timeout="8" url="http://www.ebay.dk">Thank you for your input!</exit>

    <exit cond="qualified and co=='de'" timeout="8" url="http://www.ebay.de">Survey Completed - Thank you for your time and opinions!</exit>
    <exit cond="terminated and co=='de'" timeout="8" url="http://www.ebay.de">Thank you for your input!</exit>
    <exit cond="overquota and co=='de'" timeout="8" url="http://www.ebay.de">Thank you for your input!</exit>

    <exit cond="qualified and co=='fi'" timeout="8" url="http://www.ebay.fi">Survey Completed - Thank you for your time and opinions!</exit>
    <exit cond="terminated and co=='fi'" timeout="8" url="http://www.ebay.fi">Thank you for your input!</exit>
    <exit cond="overquota and co=='fi'" timeout="8" url="http://www.ebay.fi">Thank you for your input!</exit>

    <exit cond="qualified and co=='fr'" timeout="8" url="http://www.ebay.fr">Survey Completed - Thank you for your time and opinions!</exit>
    <exit cond="terminated and co=='fr'" timeout="8" url="http://www.ebay.fr">Thank you for your input!</exit>
    <exit cond="overquota and co=='fr'" timeout="8" url="http://www.ebay.fr">Thank you for your input!</exit>

    <exit cond="qualified and co=='no'" timeout="8" url="http://www.ebay.no">Survey Completed - Thank you for your time and opinions!</exit>
    <exit cond="terminated and co=='no'" timeout="8" url="http://www.ebay.no">Thank you for your input!</exit>
    <exit cond="overquota and co=='no'" timeout="8" url="http://www.ebay.no">Thank you for your input!</exit>

    <exit cond="qualified and co=='es'" timeout="8" url="http://www.ebay.es">Survey Completed - Thank you for your time and opinions!</exit>
    <exit cond="terminated and co=='es'" timeout="8" url="http://www.ebay.es">Thank you for your input!</exit>
    <exit cond="overquota and co=='es'" timeout="8" url="http://www.ebay.es">Thank you for your input!</exit>

    <exit cond="qualified and co=='se'" timeout="8" url="http://www.ebay.se">Survey Completed - Thank you for your time and opinions!</exit>
    <exit cond="terminated and co=='se'" timeout="8" url="http://www.ebay.se">Thank you for your input!</exit>
    <exit cond="overquota and co=='se'" timeout="8" url="http://www.ebay.se">Thank you for your input!</exit>

    <exit cond="qualified and co=='uk'" timeout="8" url="http://www.ebay.co.uk">Survey Completed - Thank you for your time and opinions!</exit>
    <exit cond="terminated and co=='uk'" timeout="8" url="http://www.ebay.co.uk">Thank you for your input!</exit>
    <exit cond="overquota and co=='uk'" timeout="8" url="http://www.ebay.co.uk">Thank you for your input!</exit>

    <exit cond="qualified and co=='us'" timeout="8" url="http://www.ebay.com">Survey Completed - Thank you for your time and opinions!</exit>
    <exit cond="terminated and co=='us'" timeout="8" url="http://www.ebay.com">Thank you for your input!</exit>
    <exit cond="overquota and co=='us'" timeout="8" url="http://www.ebay.com">Thank you for your input!</exit>
  </samplesource>
</samplesources>


<html label="StandardIntro" where="survey">Thank you for taking the time to complete this survey. Your opinions are extremely valuable, and will help us to improve your eBay experience. Your responses are completely confidential and will only be used for research purposes. Your responses will be analyzed only in combination with those of other participants. See our <a href="http://pages.ebay.com/help/policies/privacy-policy.html" target="_blank">privacy policy</a>.</html>
<suspend/>

"""


    FOOTER = """<marker name="qualified"/>

</survey>"""
    
    print "%s\n\n%s\n\n%s" % (HEADER, input, FOOTER)
    
except Exception, e:
    print e

