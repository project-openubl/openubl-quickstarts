/**
 * Copyright 2019 Project OpenUBL, Inc. and/or its affiliates
 * and other contributors as indicated by the @author tags.
 * <p>
 * Licensed under the Eclipse Public License - v 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * https://www.eclipse.org/legal/epl-2.0/
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.project.openubl.quickstart.xbuilder.jee;

import io.github.project.openubl.xbuilder.content.models.standard.general.Invoice;
import io.github.project.openubl.xbuilder.enricher.ContentEnricher;
import io.github.project.openubl.xbuilder.enricher.config.DateProvider;
import io.github.project.openubl.xbuilder.enricher.config.Defaults;
import io.github.project.openubl.xbuilder.renderer.TemplateProducer;
import io.quarkus.qute.Template;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.math.BigDecimal;
import java.time.LocalDate;

@ApplicationScoped
@Path("/")
public class UBLResource {

    Defaults defaults = Defaults.builder()
            .moneda("PEN")
            .unidadMedida("NIU")
            .icbTasa(new BigDecimal("0.2"))
            .igvTasa(new BigDecimal("0.18"))
            .build();

    DateProvider dateProvider = LocalDate::now;

    @Inject
    UBLService ublService;

    @POST
    @Path("/create-xml")
    @Produces("text/plain")
    public String createInvoice(String client) {
        // Invoice generation
        Invoice invoice = ublService.createInvoice(client);

        // Enrich data
        ContentEnricher enricher = new ContentEnricher(defaults, dateProvider);
        enricher.enrich(invoice);

        // Generate XML
        Template template = TemplateProducer.getInstance().getInvoice();
        String xml = template.data(invoice).render();

        return xml;
    }

}
