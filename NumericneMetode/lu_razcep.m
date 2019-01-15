function lu_razcep

check = struct();
% NE SPREMINJAJ prvih dveh vrstic

%
% =============================================================================
% LU razcep
% =====================================================================@018954=
% 1. podnaloga
% Napisi funkcijo `[L, U] = lurazcep(A)`, ki izracuna $LU$
% razcep matrike $A$ brez pivotiranja. Delovanje preveri z 
% Matlabovo funkcijo `lu`.
% =============================================================================

% =====================================================================@018957=
% 2. podnaloga
% Napisi funkcijo `x = resi_z_lu(L, U, b)`,
% ki s pomocjo ze izracunanega $A=LU$ razcepa 
% in desne strani $b$ resi sistem $Ax=b$.
% =============================================================================
    function [L,U] = lu_razcep(A)
        [n,m] = size(A);
        L = eye(n);
        for j = (1:n-1)
            a_jj = A(j,j)
            %if (a_jj < eps) warning('Ni veljavna matrika') end
           for i = (j+1:n)
               l_ij = A(i,j)./a_jj
               L(i,j) = l_ij;
               for k = (j+1:n)
                  A(i,k) = A(i,k) - l_ij*A(j,k)
               end
           end
        end
        U = triu(A);
    end


    function x = resi_z_lu(L, U, b)
        %Ly = b 
        n = length(b);
        y = zeros(n,1)
        for i = (1:n)
            k = (1:i-1);
            s = sum (L(i,k)*y(k));
            y(i) = b(i) - s;
        end
        
        
        %Ux = by
        x = zeros(n,1)
        for i = (n:-1:1)
           k = (i+1:n);
           s = sum (U(i,k)*x(k));
           
           x(i) = (1 / U(i,i)) * (y(i) - s); 
        end
        
        
    end

A = [1,1,4;
     2,4,0;
     4,-2,1
     ];

[L,U] = lu_razcep(A)

x = resi_z_lu(L,U, [1,1,1])
A*x














































































































































% ============================================================================@


% Ce vam Octave/Matlab javi, da je v tej vrstici napaka,
% se napaka v resnici skriva v zadnjih vrsticah vase kode.

% 'Kode od tu naprej NE SPREMINJAJTE!';




if exist('OCTAVE_VERSION','builtin')
  src = char(fileread(mfilename('fullpathext')));
else
  % mfilename in Matlab does not return .m
  src = char(fileread(strcat(mfilename('fullpathext'),'.m')));
end

validate_current_attempt_file(src);

% check.m
function b = check_has_solution(part)
  b = length(strtrim(part.solution))>0;
end

function check_initialize(parts)
  check.parts = parts;
  for i =1:length(parts)
    check.parts{i}.valid = 1;
    check.parts{i}.feedback = cell(0);
    check.parts{i}.secret = cell(0);
  end
  check.part_counter = 0;
end

function r = check_part()
  check.part_counter = check.part_counter + 1;
  check.current_part = check.parts{check.part_counter};
  r = check_has_solution(check.current_part);
end

function check_error(message)
  check.parts{check.part_counter}.valid = 0;
  check_feedback(message);
end

function check_feedback(message)
	% append feedbact
  check.parts{check.part_counter}.feedback(end+1) = {message};
end

function res = check_equal(koda, rezultat, tol)
  res = 0;
  if nargin<3, tol = 1e-6; end
  actual_result = eval(koda);
  if any(size(actual_result) ~= size(rezultat)) || (norm(actual_result - rezultat) > tol) || any(any(isnan(actual_result) ~= isnan(rezultat)))
    check_error(['Izraz ', koda, ' vrne ', mat2str(actual_result), ' namesto ', mat2str(rezultat)]);
    res = 1;
  end
end

function check_secret(x,hint)
  if nargin<2, hint=''; end  
  check.parts{check.part_counter}.secret{end+1} = x;
end

function res = check_substring(koda, podniz)
  res = 0;
  if ~isempty(strfind(koda,podniz))
    check_error(['Resitev ne sme vsebovati niza ',podniz]);
    res = 1;
  end
end

function check_summarize()
  for i=1:check.part_counter
    if not(check_has_solution(check.parts{i}))
      fprintf('%d. podnaloga je brez resitve.\n', i);
    elseif not(check.parts{i}.valid)
      fprintf('%d. podnaloga nima veljavne resitve.\n',i);
    else
      fprintf('%d. podnaloga ima veljavno resitev.\n', i);
    end
    for j = 1:length(check.parts{i}.feedback)
      fprintf('  - %d ',j);
      disp(char(check.parts{i}.feedback(j)));
    end
  end
end

% check.m
% varargin2struct.m
function opt=varargin2struct(varargin)
%
% opt=varargin2struct('param1',value1,'param2',value2,...)
%   or
% opt=varargin2struct(...,optstruct,...)
%
% convert a series of input parameters into a structure
%
% authors:Qianqian Fang (fangq<at> nmr.mgh.harvard.edu)
% date: 2012/12/22
%
% input:
%      'param', value: the input parameters should be pairs of a string and a value
%       optstruct: if a parameter is a struct, the fields will be merged to the output struct
%
% output:
%      opt: a struct where opt.param1=value1, opt.param2=value2 ...
%
% license:
%     BSD or GPL version 3, see LICENSE_{BSD,GPLv3}.txt files for details
%
% -- this function is part of jsonlab toolbox (http://iso2mesh.sf.net/cgi-bin/index.cgi?jsonlab)
%

len=length(varargin);
opt=struct;
if(len==0) return; end
i=1;
while(i<=len)
    if(isstruct(varargin{i}))
        opt=mergestruct(opt,varargin{i});
    elseif(ischar(varargin{i}) && i<len)
        opt=setfield(opt,lower(varargin{i}),varargin{i+1});
        i=i+1;
    else
        error('input must be in the form of ...,''name'',value,... pairs or structs');
    end
    i=i+1;
end
end
%jsonopt.m
function val=jsonopt(key,default,varargin)
%
% val=jsonopt(key,default,optstruct)
%
% setting options based on a struct. The struct can be produced
% by varargin2struct from a list of 'param','value' pairs
%
% authors:Qianqian Fang (fangq<at> nmr.mgh.harvard.edu)
%
% $Id: loadjson.m 371 2012-06-20 12:43:06Z fangq $
%
% input:
%      key: a string with which one look up a value from a struct
%      default: if the key does not exist, return default
%      optstruct: a struct where each sub-field is a key
%
% output:
%      val: if key exists, val=optstruct.key; otherwise val=default
%
% license:
%     BSD or GPL version 3, see LICENSE_{BSD,GPLv3}.txt files for details
%
% -- this function is part of jsonlab toolbox (http://iso2mesh.sf.net/cgi-bin/index.cgi?jsonlab)
%

val=default;
if(nargin<=2) return; end
opt=varargin{1};
if(isstruct(opt))
    if(isfield(opt,key))
       val=getfield(opt,key);
    elseif(isfield(opt,lower(key)))
       val=getfield(opt,lower(key));
    end
end
end
% savejson.m
function json=savejson(rootname,obj,varargin)
%
% json=savejson(rootname,obj,filename)
%    or
% json=savejson(rootname,obj,opt)
% json=savejson(rootname,obj,'param1',value1,'param2',value2,...)
%
% convert a MATLAB object (cell, struct or array) into a JSON (JavaScript
% Object Notation) string
%
% author: Qianqian Fang (fangq<at> nmr.mgh.harvard.edu)
% created on 2011/09/09
%
% $Id$
%
% input:
%      rootname: the name of the root-object, when set to '', the root name
%        is ignored, however, when opt.ForceRootName is set to 1 (see below),
%        the MATLAB variable name will be used as the root name.
%      obj: a MATLAB object (array, cell, cell array, struct, struct array,
%      class instance).
%      filename: a string for the file name to save the output JSON data.
%      opt: a struct for additional options, ignore to use default values.
%        opt can have the following fields (first in [.|.] is the default)
%
%        opt.FileName [''|string]: a file name to save the output JSON data
%        opt.FloatFormat ['%.10g'|string]: format to show each numeric element
%                         of a 1D/2D array;
%        opt.ArrayIndent [1|0]: if 1, output explicit data array with
%                         precedent indentation; if 0, no indentation
%        opt.ArrayToStruct[0|1]: when set to 0, savejson outputs 1D/2D
%                         array in JSON array format; if sets to 1, an
%                         array will be shown as a struct with fields
%                         "_ArrayType_", "_ArraySize_" and "_ArrayData_"; for
%                         sparse arrays, the non-zero elements will be
%                         saved to _ArrayData_ field in triplet-format i.e.
%                         (ix,iy,val) and "_ArrayIsSparse_" will be added
%                         with a value of 1; for a complex array, the
%                         _ArrayData_ array will include two columns
%                         (4 for sparse) to record the real and imaginary
%                         parts, and also "_ArrayIsComplex_":1 is added.
%        opt.ParseLogical [0|1]: if this is set to 1, logical array elem
%                         will use true/false rather than 1/0.
%        opt.SingletArray [0|1]: if this is set to 1, arrays with a single
%                         numerical element will be shown without a square
%                         bracket, unless it is the root object; if 0, square
%                         brackets are forced for any numerical arrays.
%        opt.SingletCell  [1|0]: if 1, always enclose a cell with "[]"
%                         even it has only one element; if 0, brackets
%                         are ignored when a cell has only 1 element.
%        opt.ForceRootName [0|1]: when set to 1 and rootname is empty, savejson
%                         will use the name of the passed obj variable as the
%                         root object name; if obj is an expression and
%                         does not have a name, 'root' will be used; if this
%                         is set to 0 and rootname is empty, the root level
%                         will be merged down to the lower level.
%        opt.Inf ['"$1_Inf_"'|string]: a customized regular expression pattern
%                         to represent +/-Inf. The matched pattern is '([-+]*)Inf'
%                         and $1 represents the sign. For those who want to use
%                         1e999 to represent Inf, they can set opt.Inf to '$11e999'
%        opt.NaN ['"_NaN_"'|string]: a customized regular expression pattern
%                         to represent NaN
%        opt.JSONP [''|string]: to generate a JSONP output (JSON with padding),
%                         for example, if opt.JSONP='foo', the JSON data is
%                         wrapped inside a function call as 'foo(...);'
%        opt.UnpackHex [1|0]: conver the 0x[hex code] output by loadjson
%                         back to the string form
%        opt.SaveBinary [0|1]: 1 - save the JSON file in binary mode; 0 - text mode.
%        opt.Compact [0|1]: 1- out compact JSON format (remove all newlines and tabs)
%
%        opt can be replaced by a list of ('param',value) pairs. The param
%        string is equivallent to a field in opt and is case sensitive.
% output:
%      json: a string in the JSON format (see http://json.org)
%
% examples:
%      jsonmesh=struct('MeshNode',[0 0 0;1 0 0;0 1 0;1 1 0;0 0 1;1 0 1;0 1 1;1 1 1],...
%               'MeshTetra',[1 2 4 8;1 3 4 8;1 2 6 8;1 5 6 8;1 5 7 8;1 3 7 8],...
%               'MeshTri',[1 2 4;1 2 6;1 3 4;1 3 7;1 5 6;1 5 7;...
%                          2 8 4;2 8 6;3 8 4;3 8 7;5 8 6;5 8 7],...
%               'MeshCreator','FangQ','MeshTitle','T6 Cube',...
%               'SpecialData',[nan, inf, -inf]);
%      savejson('jmesh',jsonmesh)
%      savejson('',jsonmesh,'ArrayIndent',0,'FloatFormat','\t%.5g')
%
% license:
%     BSD or GPL version 3, see LICENSE_{BSD,GPLv3}.txt files for details
%
% -- this function is part of JSONLab toolbox (http://iso2mesh.sf.net/cgi-bin/index.cgi?jsonlab)
%

if(nargin==1)
   varname=inputname(1);
   obj=rootname;
   if(isempty(varname))
      varname='root';
   end
   rootname=varname;
else
   varname=inputname(2);
end
if(length(varargin)==1 && ischar(varargin{1}))
   opt=struct('filename',varargin{1});
else
   opt=varargin2struct(varargin{:});
end
opt.IsOctave=exist('OCTAVE_VERSION','builtin');
if(isfield(opt,'norowbracket'))
    warning('Option ''NoRowBracket'' is depreciated, please use ''SingletArray'' and set its value to not(NoRowBracket)');
    if(~isfield(opt,'singletarray'))
        opt.singletarray=not(opt.norowbracket);
    end
end
rootisarray=0;
rootlevel=1;
forceroot=jsonopt('ForceRootName',0,opt);
if((isnumeric(obj) || islogical(obj) || ischar(obj) || isstruct(obj) || ...
        iscell(obj) || isobject(obj)) && isempty(rootname) && forceroot==0)
    rootisarray=1;
    rootlevel=0;
else
    if(isempty(rootname))
        rootname=varname;
    end
end
if((isstruct(obj) || iscell(obj))&& isempty(rootname) && forceroot)
    rootname='root';
end

whitespaces=struct('tab',sprintf('\t'),'newline',sprintf('\n'),'sep',sprintf(',\n'));
if(jsonopt('Compact',0,opt)==1)
    whitespaces=struct('tab','','newline','','sep',',');
end
if(~isfield(opt,'whitespaces_'))
    opt.whitespaces_=whitespaces;
end

nl=whitespaces.newline;

json=obj2json(rootname,obj,rootlevel,opt);
if(rootisarray)
    json=sprintf('%s%s',json,nl);
else
    json=sprintf('{%s%s%s}\n',nl,json,nl);
end

jsonp=jsonopt('JSONP','',opt);
if(~isempty(jsonp))
    json=sprintf('%s(%s);%s',jsonp,json,nl);
end

% save to a file if FileName is set, suggested by Patrick Rapin
filename=jsonopt('FileName','',opt);
if(~isempty(filename))
    if(jsonopt('SaveBinary',0,opt)==1)
	    fid = fopen(filename, 'wb');
	    fwrite(fid,json);
    else
	    fid = fopen(filename, 'wt');
	    fwrite(fid,json,'char');
    end
    fclose(fid);
end
end
%%-------------------------------------------------------------------------
function txt=obj2json(name,item,level,varargin)

if(iscell(item))
    txt=cell2json(name,item,level,varargin{:});
elseif(isstruct(item))
    txt=struct2json(name,item,level,varargin{:});
elseif(ischar(item))
    txt=str2json(name,item,level,varargin{:});
elseif(isobject(item))
    txt=matlabobject2json(name,item,level,varargin{:});
else
    txt=mat2json(name,item,level,varargin{:});
end
end
%%-------------------------------------------------------------------------
function txt=cell2json(name,item,level,varargin)
txt={};
if(~iscell(item))
        error('input is not a cell');
end

dim=size(item);
if(ndims(squeeze(item))>2) % for 3D or higher dimensions, flatten to 2D for now
    item=reshape(item,dim(1),numel(item)/dim(1));
    dim=size(item);
end
len=numel(item);
ws=jsonopt('whitespaces_',struct('tab',sprintf('\t'),'newline',sprintf('\n'),'sep',sprintf(',\n')),varargin{:});
padding0=repmat(ws.tab,1,level);
padding2=repmat(ws.tab,1,level+1);
nl=ws.newline;
bracketlevel=~jsonopt('singletcell',1,varargin{:});
if(len>bracketlevel)
    if(~isempty(name))
        txt={padding0, '"', checkname(name,varargin{:}),'": [', nl}; name='';
    else
        txt={padding0, '[', nl};
    end
elseif(len==0)
    if(~isempty(name))
        txt={padding0, '"' checkname(name,varargin{:}) '": []'}; name='';
    else
        txt={padding0, '[]'};
    end
end
for i=1:dim(1)
    if(dim(1)>1)
        txt(end+1:end+3)={padding2,'[',nl};
    end
    for j=1:dim(2)
       txt{end+1}=obj2json(name,item{i,j},level+(dim(1)>1)+(len>bracketlevel),varargin{:});
       if(j<dim(2))
           txt(end+1:end+2)={',' nl};
       end
    end
    if(dim(1)>1)
        txt(end+1:end+3)={nl,padding2,']'};
    end
    if(i<dim(1))
        txt(end+1:end+2)={',' nl};
    end
    %if(j==dim(2)) txt=sprintf('%s%s',txt,sprintf(',%s',nl)); end
end
if(len>bracketlevel)
    txt(end+1:end+3)={nl,padding0,']'};
end
txt = sprintf('%s',txt{:});
end
%%-------------------------------------------------------------------------
function txt=struct2json(name,item,level,varargin)
txt={};
if(~isstruct(item))
	error('input is not a struct');
end
dim=size(item);
if(ndims(squeeze(item))>2) % for 3D or higher dimensions, flatten to 2D for now
    item=reshape(item,dim(1),numel(item)/dim(1));
    dim=size(item);
end
len=numel(item);
forcearray= (len>1 || (jsonopt('SingletArray',0,varargin{:})==1 && level>0));
ws=struct('tab',sprintf('\t'),'newline',sprintf('\n'));
ws=jsonopt('whitespaces_',ws,varargin{:});
padding0=repmat(ws.tab,1,level);
padding2=repmat(ws.tab,1,level+1);
padding1=repmat(ws.tab,1,level+(dim(1)>1)+forcearray);
nl=ws.newline;

if(isempty(item))
    if(~isempty(name))
        txt={padding0, '"', checkname(name,varargin{:}),'": []'};
    else
        txt={padding0, '[]'};
    end
    txt = sprintf('%s',txt{:});
    return;
end
if(~isempty(name))
    if(forcearray)
        txt={padding0, '"', checkname(name,varargin{:}),'": [', nl};
    end
else
    if(forcearray)
        txt={padding0, '[', nl};
    end
end
for j=1:dim(2)
  if(dim(1)>1)
      txt(end+1:end+3)={padding2,'[',nl};
  end
  for i=1:dim(1)
    names = fieldnames(item(i,j));
    if(~isempty(name) && len==1 && ~forcearray)
        txt(end+1:end+5)={padding1, '"', checkname(name,varargin{:}),'": {', nl};
    else
        txt(end+1:end+3)={padding1, '{', nl};
    end
    if(~isempty(names))
      for e=1:length(names)
	    txt{end+1}=obj2json(names{e},item(i,j).(names{e}),...
             level+(dim(1)>1)+1+forcearray,varargin{:});
        if(e<length(names))
            txt{end+1}=',';
        end
        txt{end+1}=nl;
      end
    end
    txt(end+1:end+2)={padding1,'}'};
    if(i<dim(1))
        txt(end+1:end+2)={',' nl};
    end
  end
  if(dim(1)>1)
      txt(end+1:end+3)={nl,padding2,']'};
  end
  if(j<dim(2))
      txt(end+1:end+2)={',' nl};
  end
end
if(forcearray)
    txt(end+1:end+3)={nl,padding0,']'};
end
txt = sprintf('%s',txt{:});
end
%%-------------------------------------------------------------------------
function txt=str2json(name,item,level,varargin)
txt={};
if(~ischar(item))
        error('input is not a string');
end
item=reshape(item, max(size(item),[1 0]));
len=size(item,1);
ws=struct('tab',sprintf('\t'),'newline',sprintf('\n'),'sep',sprintf(',\n'));
ws=jsonopt('whitespaces_',ws,varargin{:});
padding1=repmat(ws.tab,1,level);
padding0=repmat(ws.tab,1,level+1);
nl=ws.newline;
sep=ws.sep;

if(~isempty(name))
    if(len>1)
        txt={padding1, '"', checkname(name,varargin{:}),'": [', nl};
    end
else
    if(len>1)
        txt={padding1, '[', nl};
    end
end
for e=1:len
    val=escapejsonstring(item(e,:));
    if(len==1)
        obj=['"' checkname(name,varargin{:}) '": ' '"',val,'"'];
        if(isempty(name))
            obj=['"',val,'"'];
        end
        txt(end+1:end+2)={padding1, obj};
    else
        txt(end+1:end+4)={padding0,'"',val,'"'};
    end
    if(e==len)
        sep='';
    end
    txt{end+1}=sep;
end
if(len>1)
    txt(end+1:end+3)={nl,padding1,']'};
end
txt = sprintf('%s',txt{:});
end
%%-------------------------------------------------------------------------
function txt=mat2json(name,item,level,varargin)
if(~isnumeric(item) && ~islogical(item))
        error('input is not an array');
end
ws=struct('tab',sprintf('\t'),'newline',sprintf('\n'),'sep',sprintf(',\n'));
ws=jsonopt('whitespaces_',ws,varargin{:});
padding1=repmat(ws.tab,1,level);
padding0=repmat(ws.tab,1,level+1);
nl=ws.newline;
sep=ws.sep;

if(length(size(item))>2 || issparse(item) || ~isreal(item) || ...
   (isempty(item) && any(size(item))) ||jsonopt('ArrayToStruct',0,varargin{:}))
    if(isempty(name))
    	txt=sprintf('%s{%s%s"_ArrayType_": "%s",%s%s"_ArraySize_": %s,%s',...
              padding1,nl,padding0,class(item),nl,padding0,regexprep(mat2str(size(item)),'\s+',','),nl);
    else
    	txt=sprintf('%s"%s": {%s%s"_ArrayType_": "%s",%s%s"_ArraySize_": %s,%s',...
              padding1,checkname(name,varargin{:}),nl,padding0,class(item),nl,padding0,regexprep(mat2str(size(item)),'\s+',','),nl);
    end
else
    if(numel(item)==1 && jsonopt('SingletArray',0,varargin{:})==0 && level>0)
        numtxt=regexprep(regexprep(matdata2json(item,level+1,varargin{:}),'^\[',''),']','');
    else
        numtxt=matdata2json(item,level+1,varargin{:});
    end
    if(isempty(name))
    	txt=sprintf('%s%s',padding1,numtxt);
    else
        if(numel(item)==1 && jsonopt('SingletArray',0,varargin{:})==0)
           	txt=sprintf('%s"%s": %s',padding1,checkname(name,varargin{:}),numtxt);
        else
    	    txt=sprintf('%s"%s": %s',padding1,checkname(name,varargin{:}),numtxt);
        end
    end
    return;
end
dataformat='%s%s%s%s%s';

if(issparse(item))
    [ix,iy]=find(item);
    data=full(item(find(item)));
    if(~isreal(item))
       data=[real(data(:)),imag(data(:))];
       if(size(item,1)==1)
           % Kludge to have data's 'transposedness' match item's.
           % (Necessary for complex row vector handling below.)
           data=data';
       end
       txt=sprintf(dataformat,txt,padding0,'"_ArrayIsComplex_": ','1', sep);
    end
    txt=sprintf(dataformat,txt,padding0,'"_ArrayIsSparse_": ','1', sep);
    if(size(item,1)==1)
        % Row vector, store only column indices.
        txt=sprintf(dataformat,txt,padding0,'"_ArrayData_": ',...
           matdata2json([iy(:),data'],level+2,varargin{:}), nl);
    elseif(size(item,2)==1)
        % Column vector, store only row indices.
        txt=sprintf(dataformat,txt,padding0,'"_ArrayData_": ',...
           matdata2json([ix,data],level+2,varargin{:}), nl);
    else
        % General case, store row and column indices.
        txt=sprintf(dataformat,txt,padding0,'"_ArrayData_": ',...
           matdata2json([ix,iy,data],level+2,varargin{:}), nl);
    end
else
    if(isreal(item))
        txt=sprintf(dataformat,txt,padding0,'"_ArrayData_": ',...
            matdata2json(item(:)',level+2,varargin{:}), nl);
    else
        txt=sprintf(dataformat,txt,padding0,'"_ArrayIsComplex_": ','1', sep);
        txt=sprintf(dataformat,txt,padding0,'"_ArrayData_": ',...
            matdata2json([real(item(:)) imag(item(:))],level+2,varargin{:}), nl);
    end
end
txt=sprintf('%s%s%s',txt,padding1,'}');
end
%%-------------------------------------------------------------------------
function txt=matlabobject2json(name,item,level,varargin)
if numel(item) == 0 %empty object
    st = struct();
else
    % "st = struct(item);" would produce an inmutable warning, because it
    % make the protected and private properties visible. Instead we get the
    % visible properties
    propertynames = properties(item);
    for p = 1:numel(propertynames)
        for o = numel(item):-1:1 % aray of objects
            st(o).(propertynames{p}) = item(o).(propertynames{p});
        end
    end
end
txt=struct2json(name,st,level,varargin{:});
end
%%-------------------------------------------------------------------------
function txt=matdata2json(mat,level,varargin)

ws=struct('tab',sprintf('\t'),'newline',sprintf('\n'),'sep',sprintf(',\n'));
ws=jsonopt('whitespaces_',ws,varargin{:});
tab=ws.tab;
nl=ws.newline;

if(size(mat,1)==1)
    pre='';
    post='';
    level=level-1;
else
    pre=sprintf('[%s',nl);
    post=sprintf('%s%s]',nl,repmat(tab,1,level-1));
end

if(isempty(mat))
    txt='null';
    return;
end
floatformat=jsonopt('FloatFormat','%.10g',varargin{:});
%if(numel(mat)>1)
    formatstr=['[' repmat([floatformat ','],1,size(mat,2)-1) [floatformat sprintf('],%s',nl)]];
%else
%    formatstr=[repmat([floatformat ','],1,size(mat,2)-1) [floatformat sprintf(',\n')]];
%end

if(nargin>=2 && size(mat,1)>1 && jsonopt('ArrayIndent',1,varargin{:})==1)
    formatstr=[repmat(tab,1,level) formatstr];
end

txt=sprintf(formatstr,mat');
txt(end-length(nl):end)=[];
if(islogical(mat) && jsonopt('ParseLogical',0,varargin{:})==1)
   txt=regexprep(txt,'1','true');
   txt=regexprep(txt,'0','false');
end
%txt=regexprep(mat2str(mat),'\s+',',');
%txt=regexprep(txt,';',sprintf('],\n['));
% if(nargin>=2 && size(mat,1)>1)
%     txt=regexprep(txt,'\[',[repmat(sprintf('\t'),1,level) '[']);
% end
txt=[pre txt post];
if(any(isinf(mat(:))))
    txt=regexprep(txt,'([-+]*)Inf',jsonopt('Inf','"$1_Inf_"',varargin{:}));
end
if(any(isnan(mat(:))))
    txt=regexprep(txt,'NaN',jsonopt('NaN','"_NaN_"',varargin{:}));
end
end
%%-------------------------------------------------------------------------
function newname=checkname(name,varargin)
isunpack=jsonopt('UnpackHex',1,varargin{:});
newname=name;
if(isempty(regexp(name,'0x([0-9a-fA-F]+)_','once')))
    return
end
if(isunpack)
    isoct=jsonopt('IsOctave',0,varargin{:});
    if(~isoct)
        newname=regexprep(name,'(^x|_){1}0x([0-9a-fA-F]+)_','${native2unicode(hex2dec($2))}');
    else
        pos=regexp(name,'(^x|_){1}0x([0-9a-fA-F]+)_','start');
        pend=regexp(name,'(^x|_){1}0x([0-9a-fA-F]+)_','end');
        if(isempty(pos))
            return;
        end
        str0=name;
        pos0=[0 pend(:)' length(name)];
        newname='';
        for i=1:length(pos)
            newname=[newname str0(pos0(i)+1:pos(i)-1) char(hex2dec(str0(pos(i)+3:pend(i)-1)))];
        end
        if(pos(end)~=length(name))
            newname=[newname str0(pos0(end-1)+1:pos0(end))];
        end
    end
end
end
%%-------------------------------------------------------------------------
function newstr=escapejsonstring(str)
newstr=str;
isoct=exist('OCTAVE_VERSION','builtin');
if(isoct)
   vv=sscanf(OCTAVE_VERSION,'%f');
   if(vv(1)>=3.8)
       isoct=0;
   end
end
if(isoct)
  escapechars={'\\','\"','\/','\a','\f','\n','\r','\t','\v'};
  for i=1:length(escapechars);
    newstr=regexprep(newstr,escapechars{i},escapechars{i});
  end
  newstr=regexprep(newstr,'\\\\(u[0-9a-fA-F]{4}[^0-9a-fA-F]*)','\$1');
else
  escapechars={'\\','\"','\/','\a','\b','\f','\n','\r','\t','\v'};
  for i=1:length(escapechars);
    newstr=regexprep(newstr,escapechars{i},regexprep(escapechars{i},'\\','\\\\'));
  end
  newstr=regexprep(newstr,'\\\\(u[0-9a-fA-F]{4}[^0-9a-fA-F]*)','\\$1');
end
end

% savejson.m


function parts = extract_parts(src)
    part_regex = '% =+@(?<part>\d+)=\s*\n';          % beginning of header
    part_regex = [part_regex '(?<description>(\s*%( [^\n]*)?\n)+?)']; % description
    part_regex = [part_regex '(\s*% ---+\s*\n'];     % optional beginning of template
    part_regex = [part_regex '(?<template>(\s*%( [^\n]*)?\n)*))?']; % solution template
    part_regex = [part_regex '\s*% =+\s*?\n'];       % end of header
    part_regex = [part_regex '(?<solution>.*?)'];    % solution
    part_regex = [part_regex '\s*check_part\s*\(\s*\)\s*;?\s*\n']; % beginning of validation
    part_regex = [part_regex '(?<validation>.*?)'];  % validation
    part_regex = [part_regex '(?=\n\s*(% )?% =+@)']; % beginning of next part
    [s, e, te, m, t, nm, sp] = regexp(src,part_regex,'dotall');
    for i=1:length(nm)
        tmpnm = nm(i);
        if ~exist('tmpnm.template')
            tmpnm.template = '';
        end
        parts{i} =  struct('part', tmpnm.part,'solution', strtrim(tmpnm.solution),...
            'validation', strtrim(tmpnm.validation),...
            'description', regexprep(strtrim(tmpnm.description),'^\s*% ?','','lineanchors'),...
            'template', regexprep(regexprep(strtrim(tmpnm.template), '^\s*% -+\n', ''), '^\s*% ?','','lineanchors'));
    end
    % The last solution extends all the way to the validation code,
    % so we strip any trailing whitespace from it.
end


function parts = extract_solutions(src)
    part_regex = '% =+@(?<part>\d+)=\s*\n';          % beginning of header
    part_regex = [part_regex '(\s*%( [^\n]*)?\n)+?']; % description
    part_regex = [part_regex '\s*% =+\s*?\n'];       % end of header
    part_regex = [part_regex '(?<solution>.*?)'];    % solution
    part_regex = [part_regex '(?=\n\s*(% )?% =+@)']; % beginning of next part
    [s, e, te, m, t, nm, sp] = regexp(src,part_regex,'dotall');
    for i=1:length(nm)
       parts{i} =  struct('part', nm(i).part, 'solution', strtrim(nm(i).solution));
    end
    % The last solution extends all the way to the validation code,
    % so we strip any trailing whitespace from it.
end


function [response,output] = submit_parts(submited_parts, url, token)
       data = savejson('',submited_parts); %.encode('utf-8')
       % test for python 3
       py_version = 'import sys; print(sys.version_info[0])';
       [r,out2] = system(['python -c "import sys; print(sys.version_info[0])"']);
       [r,out3] = system(['python3 -c "import sys; print(sys.version_info[0])"']);
       if str2num(out2) == 3
         py_cmd = 'python';
       elseif str2num(out3) == 3
         py_cmd = 'python3';
       else
         fprintf('napaka: Python ni na voljo!\nProsimo, da namestite Python 3 (www.python.org) in poskrbite, da je v sistemski poti.\n')
       end
       fprintf('Shranjujem na streznik... ')
       py_file = tempname();
       fp = fopen(py_file,'wt');
       fprintf(fp,'import json, urllib.request\n');
       fprintf(fp,'data = r"""'); 
       fwrite(fp,data); 
       fprintf(fp,'\n"""\n'); 
       fprintf(fp,'blk_data = data.encode("utf-8")\n');
       fprintf(fp,'headers = { "Authorization": "%s","content-type": "application/json" }\n',token);
       fprintf(fp,'request = urllib.request.Request("%s", data=blk_data, headers=headers)\n',url);
       fprintf(fp,'response = urllib.request.urlopen(request)\n');
       fprintf(fp,'print(response.read().decode("utf-8"))');
       fclose(fp);
       [response,output] = system([py_cmd ' ' py_file ' 2>&1']);
       if response
         disp('PRI SHRANJEVANJU SE JE ZGODILA NAPAKA! Poskusite znova.')
         disp(output)
       else
         disp('OK')
       end
end


function update_attempts(response)
  valid_regex = '([0-9]+): *([01])';
  [s,e,te,m,t,nm,sp] = regexp(char(response),valid_regex);
  for i = 1:length(t)
    for j = 1:length(check.parts)
      if str2num(check.parts(j).part) == str2num(t{i}{1})
        % get validation from server response
        % TODO add feedback
        check.parts(j).valid = str2num(t{i}{2});
      end
    end
  end
end


function validation = validate_current_attempt_file(src)
  file_parts = extract_solutions(src);
  check_initialize(file_parts);
  
      if check_part()
        check.current_part.token = 'eyJ1c2VyIjoxMDIyLCJwYXJ0IjoxODk1NH0:1gNbjo:vyCo-F127Cdsd-AyeJvg_iGzr6k';
        try
              check_equal('zmnozi(3, 7)', 21);
  check_equal('zmnozi(6, 7)', 42);
  check_equal('zmnozi(10, 10)', 100);
  check_secret(zmnozi(100, 100));
  check_secret(zmnozi(500, 123));
          catch err
              check_error(['Testi sprozijo izjemo\n' err.message]);
          end
      end
  
      if check_part()
        check.current_part.token = 'eyJ1c2VyIjoxMDIyLCJwYXJ0IjoxODk1N30:1gNbjo:QXe67J5p04XBfGw7GiMTGHPdVIA';
        try
              check_equal('zmnozi2(3, 7)', 21);
  check_equal('zmnozi2(6, 7)', 42);
  check_equal('zmnozi2(10, 10)', 100);
  check_secret(zmnozi2(100, 100));
  check_secret(zmnozi2(500, 123));
          catch err
              check_error(['Testi sprozijo izjemo\n' err.message]);
          end
      end
  
  submited_parts = cell(0, 0);
  for part = check.parts
    part = part{1};
    if check_has_solution(part)
        part.feedback = savejson('',part.feedback);
        submited_parts{end+1} = part;
     end
  end

  url = 'https://www.projekt-tomo.si/api/attempts/submit/';
  token = 'Token 020d857dd383f838c02c4627a345f6c4aace55c1';

  response = submit_parts(submited_parts, url, token);
  update_attempts(response);
  check_summarize()
end

end 	% attempt.m
