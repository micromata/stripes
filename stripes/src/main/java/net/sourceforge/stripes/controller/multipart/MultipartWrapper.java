/* Copyright 2005-2006 Tim Fennell
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.sourceforge.stripes.controller.multipart;

import net.sourceforge.stripes.controller.FileUploadLimitExceededException;
import net.sourceforge.stripes.action.FileBean;

import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.File;
import java.util.Enumeration;
import net.sourceforge.stripes.controller.ContentTypeRequestWrapper;

/**
 * Interface which must be implemented by classes which provide the ability to
 * parse the POST content when it is of type multipart/form-data. Provides a
 * single, pluggable wrapper interface around third party libraries which
 * provide this capability.
 *
 * @author Tim Fennell
 * @since Stripes 1.4
 */
public interface MultipartWrapper extends ContentTypeRequestWrapper {

    /**
     * Pseudo-constructor that allows the class to perform any initialization
     * necessary.
     *
     * @param request an HttpServletRequest that has a content-type of
     * multipart.
     * @param tempDir a File representing the temporary directory that can be
     * used to store file parts as they are uploaded if this is desirable
     * @param maxPostSize the size in bytes beyond which the request should not
     * be read, and a FileUploadLimitExceeded exception should be thrown
     * @throws IOException if a problem occurs processing the request of storing
     * temporary files
     * @throws FileUploadLimitExceededException if the POST content is longer
     * than the maxPostSize supplied.
     */
    void build(HttpServletRequest request, File tempDir, long maxPostSize)
            throws IOException, FileUploadLimitExceededException;

    /**
     * Fetches the names of all file parameters in the request. Note that these
     * are not the file names, but the names given to the form fields in which
     * the files are specified.
     *
     * @return the names of all file parameters in the request.
     */
    Enumeration<String> getFileParameterNames();

    /**
     * Responsible for constructing a FileBean object for the named file
     * parameter. If there is no file parameter with the specified name this
     * method should return null.
     *
     * @param name the name of the file parameter
     * @return a FileBean object wrapping the uploaded file
     */
    FileBean getFileParameterValue(String name);
}
