/*
  Copyright (c) 2015, 2017, Oracle and/or its affiliates. All rights reserved.

  The MySQL Connector/J is licensed under the terms of the GPLv2
  <http://www.gnu.org/licenses/old-licenses/gpl-2.0.html>, like most MySQL Connectors.
  There are special exceptions to the terms and conditions of the GPLv2 as it is applied to
  this software, see the FOSS License Exception
  <http://www.mysql.com/about/legal/licensing/foss-exception.html>.

  This program is free software; you can redistribute it and/or modify it under the terms
  of the GNU General Public License as published by the Free Software Foundation; version 2
  of the License.

  This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
  without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
  See the GNU General Public License for more details.

  You should have received a copy of the GNU General Public License along with this
  program; if not, write to the Free Software Foundation, Inc., 51 Franklin St, Fifth
  Floor, Boston, MA 02110-1301  USA

 */

package com.mysql.cj.x.core;

import com.mysql.cj.core.exceptions.CJException;
import com.mysql.cj.x.protobuf.Mysqlx.Error;

/**
 * An Error returned from X Plugin.
 */
public class XDevAPIError extends CJException {
    private static final long serialVersionUID = 6991120628391138584L;

    /**
     * The error message returned from the server.
     */
    private Error msg;

    public XDevAPIError(String message) {
        super(message);
    }

    public XDevAPIError(Error msg) {
        super(getFullErrorDescription(msg));
        this.msg = msg;
    }

    public XDevAPIError(XDevAPIError fromOtherThread) {
        super(getFullErrorDescription(fromOtherThread.msg), fromOtherThread);
        this.msg = fromOtherThread.msg;
    }

    /**
     * Format the error message's contents into a complete error description for the exception.
     * 
     * @param msg
     *            {@link Error}
     * @return string error message
     */
    private static String getFullErrorDescription(Error msg) {
        StringBuilder stringMessage = new StringBuilder("ERROR ");
        stringMessage.append(msg.getCode());
        stringMessage.append(" (");
        stringMessage.append(msg.getSqlState());
        stringMessage.append(") ");
        stringMessage.append(msg.getMsg());
        return stringMessage.toString();
    }

    public int getErrorCode() {
        return this.msg == null ? super.getVendorCode() : this.msg.getCode();
    }

    @Override
    public String getSQLState() {
        return this.msg == null ? super.getSQLState() : this.msg.getSqlState();
    }
}
