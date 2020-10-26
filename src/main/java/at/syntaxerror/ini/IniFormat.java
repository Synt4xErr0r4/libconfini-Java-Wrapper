package at.syntaxerror.ini;

import java.util.Objects;

public class IniFormat {
	
	/** @see IniDelimiters */
	public IniDelimiters delimiter_symbol;
	/** @see IniCommentMarker */
	public IniCommentMarker	semicolon_marker,
							hash_marker;
	/** @see IniMultiline */
	public IniMultiline multiline_nodes;
	/** @see IniSectionPaths */
	public IniSectionPaths section_paths;
	public boolean 	case_sensitive,
					no_single_quotes,
					no_double_quotes,
					no_spaces_in_names,
					implicit_is_not_empty,
					do_not_collapse_values,
					preserve_empty_quotes,
					disabled_after_space,
					disabled_can_be_implicit;
	
	public IniFormat(IniDelimiters delimiter_symbol,IniCommentMarker semicolon_marker,IniCommentMarker hash_marker,IniMultiline multiline_nodes,IniSectionPaths section_paths,
			boolean case_sensitive,boolean no_single_quotes,boolean no_double_quotes,boolean no_spaces_in_names,boolean implicit_is_not_empty,
			boolean do_not_collapse_values,boolean preserve_empty_quotes,boolean disabled_after_space,boolean disabled_can_be_implicit) {
		
		this.delimiter_symbol=delimiter_symbol;
		this.semicolon_marker=semicolon_marker;
		this.hash_marker=hash_marker;
		this.multiline_nodes=multiline_nodes;
		this.section_paths=section_paths;
		this.case_sensitive=case_sensitive;
		this.no_single_quotes=no_single_quotes;
		this.no_double_quotes=no_double_quotes;
		this.no_spaces_in_names=no_spaces_in_names;
		this.implicit_is_not_empty=implicit_is_not_empty;
		this.do_not_collapse_values=do_not_collapse_values;
		this.preserve_empty_quotes=preserve_empty_quotes;
		this.disabled_after_space=disabled_after_space;
		this.disabled_can_be_implicit=disabled_can_be_implicit;
	}
	
	public static Builder builder() {
		return new Builder();
	}
	
	public static class Builder {
		
		private IniDelimiters delimiter_symbol;
		private IniCommentMarker	semicolon_marker,
									hash_marker;
		private IniMultiline multiline_nodes;
		private IniSectionPaths section_paths;
		private boolean case_sensitive,
						no_single_quotes,
						no_double_quotes,
						no_spaces_in_names,
						implicit_is_not_empty,
						do_not_collapse_values,
						preserve_empty_quotes,
						disabled_after_space,
						disabled_can_be_implicit;
		
		private Builder() {
			delimiter_symbol=IniDelimiters.fromValue((char)0);
			semicolon_marker=hash_marker=IniCommentMarker.fromValue(0);
			multiline_nodes=IniMultiline.fromValue(0);
			section_paths=IniSectionPaths.fromValue(0);
		}
		
		public Builder setDelimiterSymbol(IniDelimiters delimiter) {
			this.delimiter_symbol=Objects.requireNonNullElse(delimiter,IniDelimiters.DEFAULT);
			return this;
		}
		
		public Builder setSemicolonMarker(IniCommentMarker semicolon_marker) {
			this.semicolon_marker=Objects.requireNonNullElse(semicolon_marker,IniCommentMarker.DEFAULT);
			return this;
		}
		public Builder setHashMarker(IniCommentMarker hash_marker) {
			this.hash_marker=Objects.requireNonNullElse(hash_marker,IniCommentMarker.DEFAULT);
			return this;
		}
		
		public Builder setMultilineNodes(IniMultiline multiline_nodes) {
			this.multiline_nodes=Objects.requireNonNullElse(multiline_nodes,IniMultiline.DEFAULT);
			return this;
		}
		
		public Builder setSectionPaths(IniSectionPaths section_paths) {
			this.section_paths=Objects.requireNonNullElse(section_paths,IniSectionPaths.DEFAULT);
			return this;
		}
		
		public Builder setCaseSensitive(boolean case_sensitive) {
			this.case_sensitive=case_sensitive;
			return this;
		}
		public Builder setNoSingleQuotes(boolean no_single_quotes) {
			this.no_single_quotes=no_single_quotes;
			return this;
		}
		public Builder setNoDoubleQuotes(boolean no_double_quotes) {
			this.no_double_quotes=no_double_quotes;
			return this;
		}
		public Builder setNoSpacesInNames(boolean no_spaces_in_names) {
			this.no_spaces_in_names=no_spaces_in_names;
			return this;
		}
		public Builder setImplicitIsNotEmpty(boolean implicit_is_not_empty) {
			this.implicit_is_not_empty=implicit_is_not_empty;
			return this;
		}
		public Builder setDoNotCollapseValues(boolean do_not_collapse_values) {
			this.do_not_collapse_values=do_not_collapse_values;
			return this;
		}
		public Builder setPreserveEmptyQuotes(boolean preserve_empty_quotes) {
			this.preserve_empty_quotes=preserve_empty_quotes;
			return this;
		}
		public Builder setDisabledAfterSpace(boolean disabled_after_space) {
			this.disabled_after_space=disabled_after_space;
			return this;
		}
		public Builder setDisabledCanBeImplicit(boolean disabled_can_be_implicit) {
			this.disabled_can_be_implicit=disabled_can_be_implicit;
			return this;
		}
		
		public IniFormat build() {
			return new IniFormat(delimiter_symbol,semicolon_marker,hash_marker,multiline_nodes,section_paths,
					case_sensitive,no_single_quotes,no_double_quotes,no_spaces_in_names,implicit_is_not_empty,
					do_not_collapse_values,preserve_empty_quotes,disabled_after_space,disabled_can_be_implicit);
		}
		
	}
	
}
