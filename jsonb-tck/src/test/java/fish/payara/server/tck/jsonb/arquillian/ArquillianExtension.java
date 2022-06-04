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
package fish.payara.server.tck.jsonb.arquillian;

import ee.jakarta.tck.json.bind.MappingTester;
import ee.jakarta.tck.json.bind.TypeContainer;
import ee.jakarta.tck.json.bind.api.model.SimplePropertyVisibilityStrategy;
import fish.payara.server.tck.jsonb.propertyextension.ArchiveAppender;
import fish.payara.server.tck.jsonb.propertyextension.ArchiveProcessor;
import org.hamcrest.Matchers;
import org.jboss.arquillian.container.spi.client.deployment.DeploymentDescription;
import org.jboss.arquillian.container.test.spi.client.deployment.ApplicationArchiveProcessor;
import org.jboss.arquillian.container.test.spi.client.deployment.AuxiliaryArchiveAppender;
import org.jboss.arquillian.container.test.spi.client.deployment.DeploymentScenarioGenerator;
import org.jboss.arquillian.core.spi.LoadableExtension;
import org.jboss.arquillian.test.spi.TestClass;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;

import java.util.Arrays;
import java.util.List;

public class ArquillianExtension implements LoadableExtension {

    @Override
    public void register(ExtensionBuilder extensionBuilder) {
        extensionBuilder.service(DeploymentScenarioGenerator.class, JsonbTcksDeploymentGenerator.class)
                .service(ApplicationArchiveProcessor.class, ArchiveProcessor.class)
                .service(AuxiliaryArchiveAppender.class, ArchiveAppender.class);
    }

    public static class JsonbTcksDeploymentGenerator implements DeploymentScenarioGenerator {

        @Override
        public List<DeploymentDescription> generate(TestClass testClass) {
            WebArchive archive = ShrinkWrap.create(WebArchive.class)
                .addPackages(true,
                        testClass.getJavaClass().getPackage().getName(),
                        Matchers.class.getPackage().getName(),
                        SimplePropertyVisibilityStrategy.class.getPackage().getName(),
                        TypeContainer.class.getPackage().getName(),
                        MappingTester.class.getPackage().getName());
            DeploymentDescription dd = new DeploymentDescription("application", archive);
            return Arrays.asList(dd);
        }
    }
}
