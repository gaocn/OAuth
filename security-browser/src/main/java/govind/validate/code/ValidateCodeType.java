package govind.validate.code;

public enum ValidateCodeType {
	SMS  {
		@Override
		public String getParamNameOnValidate() {
			return "smscode";
		}
	},
	IMAGE {
		@Override
		public String getParamNameOnValidate() {
			return "imagecode";
		}
	};

	public abstract String getParamNameOnValidate();
}
