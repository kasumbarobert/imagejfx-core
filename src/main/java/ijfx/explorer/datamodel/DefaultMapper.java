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
package ijfx.explorer.datamodel;

import ijfx.core.metadata.GenericMetaData;
import ijfx.core.metadata.MetaData;
import java.util.HashMap;

/**
 *
 * @author sapho
 */
public final class DefaultMapper implements Mapper {

    //final MetaData m;
    public HashMap<Object, Object> mapValue = new HashMap();
    public String newKey;
    public String oldKey;
    public MetaData n;

    public DefaultMapper(String key1, String key2) {
        setNewKey(key1);
        setOldKey(key2);

    }

    public DefaultMapper() {
        setNewKey(null);
        setOldKey(null);

    }

    /**
     * Create a new Metadata according to a first metadata.
     *
     * @param m
     * @return
     */
    @Override
    public MetaData map(MetaData m) {
        Object newValue = lookInsideMap(m.getValue());
        n = new GenericMetaData(newKey, newValue);
        return n;

    }

    @Override
    public void setOldKey(String key) {
        this.oldKey = key;
    }

    @Override
    public void setNewKey(String s) {
        this.newKey = s;
    }

    @Override
    public HashMap<Object, Object> getMapObject() {
        return mapValue;
    }

    public String getNewKey() {
        return newKey;
    }

    @Override
    public String getOldKey() {
        return oldKey;
    }

    /**
     * Create the mapper newValue Value:value for the creation of new Metadata
     *
     * @param oldValue
     * @param newValue
     */
    public void associatedValues(Object oldValue, Object newValue) {
        if (oldValue != null && newValue != null) {
            mapValue.put(oldValue.toString(), newValue);
        }
    }

    /**
     * Looking for the conrresponding value on the mapper
     *
     * @param oldValue
     * @return
     */
    public Object lookInsideMap(Object oldValue) {
        if (oldKey != null) {

            if (mapValue.containsKey(oldValue.toString())) {
                return mapValue.get(oldValue.toString());

            }
            return null;

        }

        return null;

    }

}
