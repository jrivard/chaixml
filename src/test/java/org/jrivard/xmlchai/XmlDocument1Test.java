/*
 * XML Chai Library
 * Copyright (c) 2021-2023 Jason D. Rivard
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package org.jrivard.xmlchai;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

public class XmlDocument1Test
{

    private XmlDocument readXmlDocument() throws IOException
    {
        final InputStream xmlFactoryTestXmlFile = this.getClass().getResourceAsStream( "XmlDocument1.xml" );
        return XmlChai.getFactory().parse( xmlFactoryTestXmlFile, AccessMode.IMMUTABLE );
    }

    @Test
    public void testLoadXml()
            throws Exception
    {
        final XmlDocument xmlDocument = readXmlDocument();

        Assertions.assertEquals( "PwmConfiguration", xmlDocument.getRootElement().getName() );
        final Optional<XmlElement> configIsEditable = xmlDocument.evaluateXpathToElement( "//property[@key='configIsEditable']" );
        Assertions.assertTrue( configIsEditable.isPresent() );
        Assertions.assertEquals( "false", configIsEditable.get().getText().orElseThrow( () -> new IOException( "parse error" ) ) );
        final List<XmlElement> allSettings = xmlDocument.evaluateXpathToElements( "//setting" );
        Assertions.assertEquals( 279, allSettings.size() );

        Assert.assertThrows(
                UnsupportedOperationException.class,
                () -> xmlDocument.getRootElement().setAttribute( "newAttribute", "newValue" ) );
    }
}
