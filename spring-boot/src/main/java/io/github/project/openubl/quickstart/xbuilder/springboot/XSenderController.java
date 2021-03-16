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
package io.github.project.openubl.quickstart.xbuilder.springboot;

import io.github.project.openubl.xmlsenderws.webservices.exceptions.InvalidXMLFileException;
import io.github.project.openubl.xmlsenderws.webservices.exceptions.UnsupportedDocumentTypeException;
import io.github.project.openubl.xmlsenderws.webservices.managers.smart.SmartBillServiceConfig;
import io.github.project.openubl.xmlsenderws.webservices.managers.smart.SmartBillServiceManager;
import io.github.project.openubl.xmlsenderws.webservices.managers.smart.SmartBillServiceModel;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class XSenderController {

    private final String username = "12345678912MODDATOS";
    private final String password = "MODDATOS";

    @PostMapping("/api/file/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file) throws IOException, InvalidXMLFileException, UnsupportedDocumentTypeException {
        SmartBillServiceConfig.getInstance()
                .withInvoiceAndNoteDeliveryURL("https://e-beta.sunat.gob.pe/ol-ti-itcpfegem-beta/billService")
                .withPerceptionAndRetentionDeliveryURL("https://e-beta.sunat.gob.pe/ol-ti-itemision-otroscpe-gem-beta/billService")
                .withDespatchAdviceDeliveryURL("https://e-beta.sunat.gob.pe/ol-ti-itemision-guia-gem-beta/billService");

        SmartBillServiceModel result = SmartBillServiceManager.send(file.getBytes(), username, password);
        return result.getXmlContentModel().getDocumentType() + " " + result.getBillServiceModel().getStatus();
    }

}
