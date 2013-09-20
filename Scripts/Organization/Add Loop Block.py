try:
    import sys
    import re

    try:
        input = str(sys.argv[1])
    except:
        input = ""().strip()

    input = re.sub(r'<(radio|checkbox|text|textarea|block|number|float|select|html)(.*) label="([^"]*)"',r'<\1\2 label="\3_[loopvar: label]"', input)

    print """
<loop label="" vars="" title=" " suspend="0">

<block label="">

%s

</block>

<looprow label="" cond="">
  <loopvar name=""></loopvar>
</looprow>

</loop>

""" % input
    
except Exception, e:
    print e

