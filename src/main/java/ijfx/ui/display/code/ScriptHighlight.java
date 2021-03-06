/*
    This file is part of ImageJ FX.

    ImageJ FX is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    ImageJ FX is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with ImageJ FX.  If not, see <http://www.gnu.org/licenses/>. 
    
     Copyright 2015,2016 Cyril MONGIS, Michael Knop
	
 */
package ijfx.ui.display.code;

import java.util.Collection;
import java.util.Hashtable;
import org.fxmisc.richtext.model.StyleSpans;
import org.scijava.script.ScriptLanguage;

/**
 *
 * @author florian
 * Classes implementing this interface should be able to return a StyleSpan of the given text computed with the given Hashtable
 */
public interface ScriptHighlight {
   
    public StyleSpans<Collection<String>> computeHighlighting(String text);
    public void setKeywords(Hashtable keywordTable);
    public void setLangage(ScriptLanguage language);
}
