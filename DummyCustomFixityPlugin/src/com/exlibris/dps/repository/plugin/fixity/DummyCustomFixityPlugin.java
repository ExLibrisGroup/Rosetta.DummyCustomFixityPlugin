package com.exlibris.dps.repository.plugin.fixity;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.exlibris.core.sdk.strings.StringUtils;
import com.exlibris.dps.repository.plugin.CustomFixityPlugin;


/**
 * A dummy implementation of the CustomFixityPlugin.
 * This implementation simply returns the file length as the checksum.
 *
 * @author PeterK
 */
public class DummyCustomFixityPlugin implements CustomFixityPlugin {

	private static final String PLUGIN_VERSION_INIT_PARAM = "PLUGIN_VERSION_INIT_PARAM";
	private String pluginVersion = null;
	private boolean result = true;

	private List<String> errors = null;

	/* (non-Javadoc)
	 * @see com.exlibris.dps.repository.plugin.CustomFixityPlugin#getErrors()
	 */
	@Override
	public List<String> getErrors() {
		return errors;
	}

	/* (non-Javadoc)
	 * @see com.exlibris.dps.repository.plugin.CustomFixityPlugin#getFixity(java.lang.String)
	 */
	@Override
	public String getFixity(String filePath, String oldFixity) throws Exception {
		File file = new File(filePath);
		String newFixity = String.valueOf(file.length() *
				((result) ? 1 : 2)); // fail fixity when needed

		if (oldFixity != null && !newFixity.equals(oldFixity)) {
			errors = new ArrayList<String>();
			errors.add("Fixity mismatch. Old fixity was " + oldFixity +
					", new fixity is " + newFixity);
		}

		return newFixity;
	}

	@Override
	public String getAlgorithm() {
		return "Dummy algorithm - File length";
	}

	@Override
	public String getAgent()
    {
    	return "Dummy custom fixity , Plugin Version " + pluginVersion;
    }

    public void initParams(Map<String, String> initParams) {
		this.pluginVersion = initParams.get(PLUGIN_VERSION_INIT_PARAM);
		if(!StringUtils.isEmptyString(initParams.get("fixityScanResult"))){
			result = Boolean.parseBoolean(initParams.get("fixityScanResult").trim());
		}
	}
}
