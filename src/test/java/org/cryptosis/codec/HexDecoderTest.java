/*
 * Licensed to Virginia Tech under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Virginia Tech licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License.  You may obtain a
 * copy of the License at the following location:
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.cryptosis.codec;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;

import org.cryptosis.util.ByteUtil;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * Unit test for {@link HexDecoder} class.
 *
 * @author Marvin S. Addison
 */
public class HexDecoderTest
{
  @DataProvider(name = "hex-data")
  public Object[][] getHexData()
  {
    return new Object[][] {
      new Object[] {
        "41626c652077617320492065726520492073617720656c6261",
        "Able was I ere I saw elba",
      },
      new Object[] {
        "41626c652 077617320492065726520492073617720656c626\n1",
        "Able was I ere I saw elba",
      },
      new Object[] {
        "41626c652 077617320492065726520492073617720656c626\n",
        "Able was I ere I saw elb",
      },
      new Object[] {
        "41:62:6c:65:20:77:61:73:20:49:20:65:72:65:20:49:20:73:61:77:20:65:6c:62:61",
        "Able was I ere I saw elba",
      },
      new Object[] {
        "9c63b0547798b60d5e04",
        ByteUtil.toString(new byte[] {
          (byte) -100, (byte)  99, (byte) -80, (byte) 84, (byte) 119,
          (byte) -104, (byte) -74, (byte)  13, (byte) 94, (byte)   4,
        }),
      },
    };
  }

  @Test(dataProvider = "hex-data")
  public void testDecode(final String encoded, final String expected) throws Exception
  {
    final HexDecoder decoder = new HexDecoder();
    final ByteBuffer output = ByteBuffer.allocate(decoder.outputSize(encoded.length()));
    decoder.decode(CharBuffer.wrap(encoded), output);
    decoder.finalize(output);
    output.flip();
    assertEquals(ByteUtil.toString(output), expected);
  }
}
