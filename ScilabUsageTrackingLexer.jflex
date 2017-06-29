//CHECKSTYLE:0FF

package org.scilab.modules.scinotes;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Map;
import java.util.HashMap;

import java.util.stream.Stream;
import java.util.stream.Collectors;
import java.util.stream.*;
import java.util.function.*;
import static java.util.stream.Collectors.toCollection;

import java.util.Timer;
import java.time.*;
import java.time.temporal.ChronoUnit;

import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import org.scilab.modules.commons.ScilabCommonsUtils;

@javax.annotation.Generated("JFlex")
@SuppressWarnings("fallthrough")

%%

%public
%class ScilabUsageTrackingLexer
%extends UsageTrackingPreference
%final
%unicode
%char
%type int
%pack

%{
	public int start;
	public int end;
	public int beginString;
    public static List<String> commandslist = new ArrayList<String>();
    public static List<String> macroslist = new ArrayList<String>();
	public static HashMap<String, Integer> commands = new HashMap<String, Integer>();
	public static HashMap<String, Integer> macros = new HashMap<String, Integer>();
	public Set<String> infile; 

	private ScilabDocument doc;
	private boolean transposable;
    private Element elem;
    private boolean breakstring;
    private boolean breakcomment;
    private MatchingBlockScanner matchBlock;

    static {
    // For Scinotes colors in preferences
    commands.put("cos", 0);
    macros.put("sind", 1);
    }

    public ScilabUsageTrackingLexer(ScilabDocument doc) {
    this(doc, new MatchingBlockScanner(doc), true);
    }

    public ScilabUsageTrackingLexer(ScilabDocument doc, boolean update) {
    this(doc, new MatchingBlockScanner(doc), update);
    }

    public ScilabUsageTrackingLexer(ScilabDocument doc, MatchingBlockScanner matchBlock, boolean update) {
    	this.doc = doc;
    	this.elem = doc.getDefaultRootElement();
    	this.infile = doc.getFunctionsInDoc();
    this.matchBlock = matchBlock;
    if (update) {
    	update();
    }
    }

    public static void update() {
    	if (ScilabCommonsUtils.isScilabThread()) {
    		String[] funs = ScilabKeywords.GetFunctionsName();
        int funslength = funs.length;
    		String[] macs = ScilabKeywords.GetMacrosName();
        int macslength = macs.length;
    		commands.clear();
    		macros.clear();
        int i=0;
    		if (funs != null) {
                commandslist = Arrays.asList(funs);
    		  for (i=0; i<funslength; i++){
                    commands.put(funs[i], i);
                }
    		}
            
    		if (macs != null) {
                macroslist = Arrays.asList(macs);
    		  for (i=0; i<macslength; i++){
                    macros.put(macs[i], i);
              }
    		}

             double totalCommands = commands.values().stream().collect(Collectors.summingInt(Integer::intValue)); 
            double totalMacros = macros.values().stream().collect(Collectors.summingInt(Integer::intValue));
            LocalDateTime currentTime = LocalDateTime.now();
            LocalDate today = LocalDate.now();
    	}
    }

    public void setRange(int p0, int p1) {
    	start = p0;
    	end = p1;
    	transposable = false;
    	breakstring = false;
    	yyreset(new ScilabDocumentReader(doc, p0, p1));
    	int currentLine = elem.getElementIndex(start);
    	if (currentLine != 0) {
    	ScilabDocument.ScilabLeafElement e = (ScilabDocument.ScilabLeafElement) elem.getElement(currentLine - 1);
    }
    }

    public int yychar() {
    	return yychar;
    }

    public int scan() throws IOException {
    	int ret = yylex();
    int lastPos = start + yychar + yylength();
    	if (lastPos == end -1) {
    		((ScilabDocument.ScilabLeafElement) elem.getElement(elem.getElementIndex(start))).setBrokenString(breakstring);
    		breakstring = false;
    	} 
    return ret;
    }

    
    public int getKeyword(int pos, boolean strict) {
    	// Pre condition
    	if (elem == null) {
    		return UsageTrackingLexerConstants.DEFAULT;
    	}

    	Element line = elem.getElement(elem.getElementIndex(pos));
    	int end = line.getEndOffset();
    	int tok = -1;
    	start = line.getStartOffset();
    	int startL = start;
    	int s = -1;

    	try {
    		yyreset(new ScilabDocumentReader(doc, start, end));
    		if (!strict) {
    			pos++;
    		}

    	} catch (IOException e) {
    		return UsageTrackingLexerConstants.DEFAULT;
    	}
    }

    public static ScilabTokens getScilabTokens(String str) {
    	ScilabDocument doc = new ScilabDocument(false);
    try {
    	doc.insertString(0, str, null);
    	} catch (BadLocationException e) { }
    return getScilabTokens(doc); 
    }

    public static ScilabTokens getScilabTokens(ScilabDocument doc) {
    	ScilabUsageTrackingLexer usagelexer = new ScilabUsageTrackingLexer(doc);
    usagelexer.yyreset(new ScilabDocumentReader(doc, 0, doc.getLength()));
    ScilabTokens tokens = new ScilabTokens();
    int tok = -1;
    try {
    	while (tok != UsageTrackingLexerConstants.EOF) {
    		tok = usagelexer.yylex();
    		tokens.add(tok, usagelexer.yychar + usagelexer.yylength());
    	}
    } catch (IOException e) { }

    return tokens;
    }

    public static class ScilabTokens {
    	private List<Integer> tokenType = new ArrayList<Integer>();
    	private List<Integer> tokenPos = new ArrayList<Integer>();

    ScilabTokens() { }

    void add(final int type, final int pos) {
    	tokenType.add(type);
    	tokenPos.add(pos);
    }

    public final List<Integer> getTokenType() {
    	return tokenType;
    }

    public final List<Integer> getTokenPos() {
    	return tokenPos;
    }
     }

%}

/* main character classes */
eol = \n

functionKwds = "function" | "endfunction"

id = ([a-zA-A%_#!?][a-zA-Z0-9_#!$?]*)|("$"[a-zA-Z0-9_#!$?]+)

badid = ([0-9$][a-zA-Z0-9_#!$?]+)

url = "http://"[^ \t\f\n\r\'\"]+

openCloseStructureKwds = "if" | "for" | "while" | "try" | "select" | "switch"

%x COMMANDS

%%

<YYINITIAL> {
	
	{functionKwds}	{				
						transposable = false;
						return UsageTrackingLexerConstants.FKEYWORD;
					}

    {openCloseStructureKwds}     {
                                    transposable = false;
                                    return ScilabLexerConstants.OSKEYWORD;
                                 }


    {url}                        {
                                   return ScilabLexerConstants.URL;
                                 }



	{id}			{
						transposable = true;
						String str = yytext();
						if (commands.contains(str)) {
							yybegin(COMMANDS);
							return UsageTrackingLexerConstants.COMMANDS;						
						} else if (macros.contains(str)) {
							yybegin(COMMANDS);
							return UsageTrackingLexerConstants.MACROS;
						} else if (infile.contains(str)) {
							yybegin(COMMANDS);
							return UsageTrackingLexerConstants.MACROSINFILE;
						}
						return UsageTrackingLexerConstants.ID; 
					}

	.				|
	{eol}			{
						transposable = false;
						return UsageTrackingLexerConstants.DEFAULT;
					}

			}

<<EOF>>				{
						return UsageTrackingLexerConstants.EOF;
					}