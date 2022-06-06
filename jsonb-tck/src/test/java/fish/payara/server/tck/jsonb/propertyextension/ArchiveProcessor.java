/*
 *  DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 *  Copyright (c) [2022] Payara Foundation and/or its affiliates. All rights reserved.
 *
 *  The contents of this file are subject to the terms of either the GNU
 *  General Public License Version 2 only ("GPL") or the Common Development
 *  and Distribution License("CDDL") (collectively, the "License").  You
 *  may not use this file except in compliance with the License.  You can
 *  obtain a copy of the License at
 *  https://github.com/payara/Payara/blob/master/LICENSE.txt
 *  See the License for the specific
 *  language governing permissions and limitations under the License.
 *
 *  When distributing the software, include this License Header Notice in each
 *  file and include the License.
 *
 *  When distributing the software, include this License Header Notice in each
 *  file and include the License file at glassfish/legal/LICENSE.txt.
 *
 *  GPL Classpath Exception:
 *  The Payara Foundation designates this particular file as subject to the "Classpath"
 *  exception as provided by the Payara Foundation in the GPL Version 2 section of the License
 *  file that accompanied this code.
 *
 *  Modifications:
 *  If applicable, add the following below the License Header, with the fields
 *  enclosed by brackets [] replaced by your own identifying information:
 *  "Portions Copyright [year] [name of copyright owner]"
 *
 *  Contributor(s):
 *  If you wish your version of this file to be governed by only the CDDL or
 *  only the GPL Version 2, indicate your decision by adding "[Contributor]
 *  elects to include this software in this distribution under the [CDDL or GPL
 *  Version 2] license."  If you don't indicate a single choice of license, a
 *  recipient has the option to distribute your version of this file under
 *  either the CDDL, the GPL Version 2 or to extend the choice of license to
 *  its licensees as provided above.  However, if you add GPL Version 2 code
 *  and therefore, elected the GPL Version 2 license, then the option applies
 *  only if the new code is made subject to such option by the copyright
 *  holder.
 */
package fish.payara.server.tck.jsonb.propertyextension;

import org.jboss.arquillian.container.test.spi.client.deployment.ApplicationArchiveProcessor;
import org.jboss.arquillian.test.spi.TestClass;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.container.ResourceContainer;

import java.io.ByteArrayOutputStream;
import java.util.Map;
import java.util.Properties;

public class ArchiveProcessor implements ApplicationArchiveProcessor {

    @Override
    public void process(Archive<?> applicationArchive, TestClass testClass) {
        if(applicationArchive instanceof ResourceContainer) {
            ResourceContainer<?> container = (ResourceContainer<?>)applicationArchive;
            container.addAsResource(
                    new StringAsset(
                            toString(
                                    filterSystemProperties("signature.sigTestClasspath"))), SystemProperties.FILE_NAME);
        }
    }

    public Properties filterSystemProperties(String prefix) {
        Properties filteredProps = new Properties();
        Properties sysProps = System.getProperties();
        for (Map.Entry<Object, Object> entry: sysProps.entrySet())
        {
            if(entry.getKey().equals(prefix)) {
                String newKey = entry.getKey().toString(); //.replaceFirst(prefix, "");
                filteredProps.put(newKey, entry.getValue());
            }
            if (entry.getKey().equals("jimage.dir")) {
                filteredProps.put("jimage.dir", entry.getValue());
            }
        }
        return filteredProps;
    }

    public String toString(Properties props) {
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            props.store(out, "Arquillian SystemProperties Extension");
            return out.toString();
        } catch (Exception e) {
            throw new RuntimeException("Could not store properties", e);
        }
    }
}