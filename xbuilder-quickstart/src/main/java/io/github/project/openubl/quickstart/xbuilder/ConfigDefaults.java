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
package io.github.project.openubl.quickstart.xbuilder;

import io.github.project.openubl.xmlbuilderlib.config.Config;
import io.github.project.openubl.xmlbuilderlib.models.catalogs.Catalog10;
import io.github.project.openubl.xmlbuilderlib.models.catalogs.Catalog7;
import io.github.project.openubl.xmlbuilderlib.models.catalogs.Catalog9;

import java.math.BigDecimal;

public class ConfigDefaults implements Config {
    private BigDecimal igv = new BigDecimal("0.18");
    private BigDecimal ivap = new BigDecimal("0.04");
    private String defaultMoneda = "PEN";
    private String defaultUnidadMedida = "NIU";
    private Catalog9 defaultTipoNotaCredito = Catalog9.ANULACION_DE_LA_OPERACION;
    private Catalog10 defaultTipoNotaDebito = Catalog10.AUMENTO_EN_EL_VALOR;
    private BigDecimal defaultIcb = new BigDecimal("0.2");
    private Catalog7 defaultTipoIgv = Catalog7.GRAVADO_OPERACION_ONEROSA;

    @Override
    public BigDecimal getIgv() {
        return igv;
    }

    @Override
    public BigDecimal getIvap() {
        return ivap;
    }

    @Override
    public String getDefaultMoneda() {
        return defaultMoneda;
    }

    @Override
    public String getDefaultUnidadMedida() {
        return defaultUnidadMedida;
    }

    @Override
    public Catalog9 getDefaultTipoNotaCredito() {
        return defaultTipoNotaCredito;
    }

    @Override
    public Catalog10 getDefaultTipoNotaDebito() {
        return defaultTipoNotaDebito;
    }

    @Override
    public BigDecimal getDefaultIcb() {
        return defaultIcb;
    }

    @Override
    public Catalog7 getDefaultTipoIgv() {
        return defaultTipoIgv;
    }
}
