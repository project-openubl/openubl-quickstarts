/**
 * Copyright 2019 Project OpenUBL, Inc. and/or its affiliates
 * and other contributors as indicated by the @author tags.
 *
 * Licensed under the Eclipse Public License - v 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.project.openubl.quickstart.jee;

import io.github.project.openubl.quickstart.jee.config.UBLConfigSingleton;
import io.github.project.openubl.xmlbuilderlib.clock.SystemClock;
import io.github.project.openubl.xmlbuilderlib.config.Config;
import io.github.project.openubl.xmlbuilderlib.facade.DocumentManager;
import io.github.project.openubl.xmlbuilderlib.facade.DocumentWrapper;
import io.github.project.openubl.xmlbuilderlib.models.input.standard.invoice.InvoiceInputModel;
import io.github.project.openubl.xmlbuilderlib.models.output.standard.invoice.InvoiceOutputModel;

import javax.inject.Inject;
import javax.ws.rs.*;

@Path("/")
public class UBLResource {

    @Inject
    UBLService ublService;

    @POST
    @Path("/create-xml")
    @Produces("text/plain")
    public String createInvoice(String client) {
        // General config
        Config config = UBLConfigSingleton.getInstance().getConfig();
        SystemClock clock = UBLConfigSingleton.getInstance().getClock();

        // Invoice generation
        InvoiceInputModel input = ublService.createInvoice(client);
        DocumentWrapper<InvoiceOutputModel> result = DocumentManager.createXML(input, config, clock);
        return result.getXml();
    }

}
