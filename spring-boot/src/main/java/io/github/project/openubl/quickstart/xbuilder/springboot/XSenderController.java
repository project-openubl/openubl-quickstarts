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
