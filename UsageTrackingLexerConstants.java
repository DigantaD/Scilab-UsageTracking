/*
 * Scilab ( http://www.scilab.org/ ) - This file is part of Scilab
 * Copyright (C) 2010 - Calixte DENIZET
 *
 * Copyright (C) 2012 - 2016 - Scilab Enterprises
 *
 * This file is hereby licensed under the terms of the GNU GPL v2.0,
 * pursuant to article 5.3.4 of the CeCILL v.2.1.
 * This file was originally licensed under the terms of the CeCILL v2.1,
 * and continues to be available under such terms.
 * For more information, see the COPYING file which you should have received
 * along with this program.
 *
 */

package org.scilab.modules.scinotes;

import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;

/**
 *This class contains constants used in the lexer
 */
public class UsageTrackingLexerConstants {

	/** 
	 * Number of known tokens
	 */
	public static final int NUMBEROFTOKENS = 9;

	/**
	 * DEFAULT : tokens which are not recognized
	 */
	public static final int DEFAULT = 0;

	/**
	 * COMMANDS : Built-in functions in SCILAB
	 */
	public static final int COMMANDS = 1;

	/**
	 * MACROS : Macros in SCILAB
	 */
	public static final int MACROS = 2;

	/**
	 * MACROSINFILE : Macros in SCILAB
	 */
	public static final int MACROSINFILE = 3;

	/**
	 * For keywords 'function' and 'endfunction'
	 */
	public static final int FKEYWORD =4;

	 /**
     * ID : Identifiers like 'myvar' or 'myfun'
     */
    public static final int ID = 5;

	/**
     * URL : http://...
     */
    public static final int URL = 6;

	/**
     * OPENCLOSE : an opening char like '(', '[', '{' or ')'
     */
    public static final int OPENCLOSE = 7;

	/**
	 * EOF = End of File
	*/
	public static final int EOF = 8;

	/** 
	 * TOKENS : A HashMap which contains the names of keywords
	 */
	public static HashMap<String, Integer> TOKENS = new HashMap<String, Integer>(9);

	private static HashMap<Integer, String> idTokens;

	static {
		TOKENS.put("Default", DEFAULT);
		TOKENS.put("Commands", COMMANDS);
		TOKENS.put("Macros", MACROS);
		TOKENS.put("Macros in file", MACROSINFILE);
		TOKENS.put("Funtions", FKEYWORD);
		TOKENS.put("Identifiers", ID);
		TOKENS.put("End of File", EOF);
		TOKENS.put("URL", URL);
		TOKENS.put("OpenClose", OPENCLOSE);
	}

	/** 
	 * @param id the type of token
	 * @return the string representation in config file of this token
	 */
	
	public final static String getStringRep(int id) {

		if (idTokens == null) {
			idTokens = new HashMap<Integer, String>(TOKENS.size());
			Iterator<String> iterator = TOKENS.keySet().iterator();
			while (iterator.hasNext()) {
				String key = iterator.next();
				idTokens.put(TOKENS.get(key), key);
			}
		}

		String rep = idTokens.get(id);
		if (rep != null) {
			return rep;
		}
		return "Default";
	}

	/**
	 * Can we have help on the keyword with type?
	 * @param type the type of the keyword
	 * @return true if the keyword in helpable
	 */

	public static final boolean isHelpable(final int type) {
		return type == ID 
			   || type == COMMANDS
			   || type == MACROS
			   || type == FKEYWORD;
	} 

	public static final boolean isSearchable(final int type) {
		return type == ID
			   || type == COMMANDS
			   || type == MACROS
			   || type == MACROSINFILE
			   || type == FKEYWORD;
	}

	/**
     * To know if a keyword is a part of a matching keywords
     * @param type the type of the keyword
     * @return true if the keyword is a part of a matching block
     */

	/**
     * To know if a keyword is a clickable
     * @param type the type of the keyword
     * @return true if the keyword is clickable
     */

	public static final boolean isClickable(final int type) {
        return type == URL
               || type == MACROS
               || type == MACROSINFILE;
    }
    
    public static final boolean isMatchable(final int type) {
        return type == OPENCLOSE
               || type == FKEYWORD;
    }

	/**
	 * Can we open the source file of the keyword with type ?
	 * @param type the type of the keyword
	 * @return true if the keyword is openable
	 */	
	
	public static final boolean isOpenable(final int type) {
		return type == MACROS || type == MACROSINFILE;
	}
}